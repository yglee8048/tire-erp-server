package com.minsoo.co.tireerpserver.service.purchase;

import com.minsoo.co.tireerpserver.api.error.exceptions.NotFoundException;
import com.minsoo.co.tireerpserver.model.code.PurchaseStatus;
import com.minsoo.co.tireerpserver.model.entity.entities.management.Brand;
import com.minsoo.co.tireerpserver.model.entity.entities.management.Pattern;
import com.minsoo.co.tireerpserver.model.entity.entities.management.Vendor;
import com.minsoo.co.tireerpserver.model.entity.entities.management.Warehouse;
import com.minsoo.co.tireerpserver.model.entity.entities.purchase.Purchase;
import com.minsoo.co.tireerpserver.model.entity.entities.purchase.PurchaseContent;
import com.minsoo.co.tireerpserver.model.entity.entities.tire.Tire;
import com.minsoo.co.tireerpserver.model.entity.entities.tire.TireDot;
import com.minsoo.co.tireerpserver.service.ServiceTest;
import com.minsoo.co.tireerpserver.service.management.BrandService;
import com.minsoo.co.tireerpserver.service.management.PatternService;
import com.minsoo.co.tireerpserver.service.stock.StockService;
import com.minsoo.co.tireerpserver.service.tire.TireDotService;
import com.minsoo.co.tireerpserver.service.tire.TireService;
import com.minsoo.co.tireerpserver.service.management.VendorService;
import com.minsoo.co.tireerpserver.service.management.WarehouseService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.minsoo.co.tireerpserver.utils.RequestBuilder.*;
import static org.assertj.core.api.Assertions.*;

class PurchaseServiceTest extends ServiceTest {

    @Autowired
    BrandService brandService;

    @Autowired
    PatternService patternService;

    @Autowired
    VendorService vendorService;

    @Autowired
    WarehouseService warehouseService;

    @Autowired
    TireService tireService;

    @Autowired
    TireDotService tireDotService;

    @Autowired
    PurchaseService purchaseService;

    @Autowired
    PurchaseContentService purchaseContentService;

    @Autowired
    StockService stockService;

    /**
     * 1. 매입이 정상적으로 생성되어야 한다.
     * 2. 매입 항목의 tire-dot 가 중복으로 요청 되는 경우 합쳐서 저장한다.
     */
    @Test
    @DisplayName("매입 생성")
    void create() {
        // GIVEN
        Vendor vendor = vendorService.create(VENDOR("테스트 매입처"));
        Brand brand = brandService.create(BRAND("테스트 브랜드"));
        Pattern pattern = patternService.create(brand.getId(), PATTERN("테스트 패턴"));
        Tire tire = tireService.create(TIRE("PRODUCT_ID_01", pattern.getId(), 11));
        TireDot tireDot01 = tireDotService.create(tire.getId(), TIRE_DOT("1111", 2000L));
        TireDot tireDot02 = tireDotService.create(tire.getId(), TIRE_DOT("2222", 4000L));
        clear();

        // WHEN
        Purchase purchase = purchaseService.create(
                PURCHASE(vendor.getId(), LocalDate.now(),
                        PURCHASE_CONTENT(tireDot01.getId(), 1L),
                        PURCHASE_CONTENT(tireDot02.getId(), 2L),
                        PURCHASE_CONTENT(tireDot01.getId(), 1L)));
        clear();

        // THEN
        assertThat(purchase.getContents().size()).isEqualTo(2);
        assertThat(purchase.getContents().stream()
                .map(PurchaseContent::getQuantity)
                .reduce(0L, Long::sum)).isEqualTo(4);
        assertThat(purchase.getVendor().getId()).isEqualTo(vendor.getId());
        assertThat(purchase.getPurchaseDate()).isEqualTo(LocalDate.now());
        clear();
    }

    /**
     * 1. 매입 일자 조건으로 검색했을 때, 정상 검색되어야 한다.
     */
    @Test
    @DisplayName("매입 목록 조회")
    void findAll() {
        // GIVEN
        Vendor vendor = vendorService.create(VENDOR("테스트 매입처"));
        Brand brand = brandService.create(BRAND("테스트 브랜드"));
        Pattern pattern = patternService.create(brand.getId(), PATTERN("테스트 패턴"));
        Tire tire = tireService.create(TIRE("PRODUCT_ID_01", pattern.getId(), 11));
        TireDot tireDot = tireDotService.create(tire.getId(), TIRE_DOT("1111", 2000L));
        clear();

        // WHEN
        purchaseService.create(PURCHASE(vendor.getId(), LocalDate.of(2021, 2, 27), PURCHASE_CONTENT(tireDot.getId(), 1L)));
        purchaseService.create(PURCHASE(vendor.getId(), LocalDate.of(2021, 2, 28), PURCHASE_CONTENT(tireDot.getId(), 1L)));
        purchaseService.create(PURCHASE(vendor.getId(), LocalDate.of(2021, 3, 1), PURCHASE_CONTENT(tireDot.getId(), 1L)));
        clear();

        // THEN
        assertThat(purchaseService.findAll(null, null).size()).isEqualTo(3);
        assertThat(purchaseService.findAll(LocalDate.of(2021, 2, 28), null).size()).isEqualTo(2);
        assertThat(purchaseService.findAll(LocalDate.of(2021, 3, 1), null).size()).isEqualTo(1);
        assertThat(purchaseService.findAll(null, LocalDate.of(2021, 2, 28)).size()).isEqualTo(2);
        assertThat(purchaseService.findAll(LocalDate.of(2021, 2, 27),
                LocalDate.of(2021, 2, 28)).size()).isEqualTo(2);
        clear();
    }

    /**
     * 1. 매입 수정 테스트
     * 1-1) 매입이 확정되기 전에는 매입 내용을 수정할 수 있다.
     * 1-2) 기존에 없는 매입 항목을 요청하면 신규로 저장한다.
     * 1-3) 기존에 있던 매입 항목은 수정한다.
     * 1-4) 기존에 있었지만 삭제된 항목은 삭제한다.
     * 1-5) tire-dot 를 기준으로 묶어서 생성/수정/삭제를 진행한다.
     */
    @Test
    @DisplayName("매입 수정")
    void update() {
        // GIVEN
        Vendor vendor = vendorService.create(VENDOR("테스트 매입처"));
        Brand brand = brandService.create(BRAND("테스트 브랜드"));
        Pattern pattern = patternService.create(brand.getId(), PATTERN("테스트 패턴"));
        Tire tire = tireService.create(TIRE("PRODUCT_ID_01", pattern.getId(), 11));
        TireDot tireDot01 = tireDotService.create(tire.getId(), TIRE_DOT("1111", 2000L));
        TireDot tireDot02 = tireDotService.create(tire.getId(), TIRE_DOT("2222", 4000L));
        TireDot tireDot03 = tireDotService.create(tire.getId(), TIRE_DOT("3333", 6000L));

        Purchase purchase = purchaseService.create(PURCHASE(vendor.getId(), LocalDate.now(),
                PURCHASE_CONTENT(tireDot01.getId(), 1L),
                PURCHASE_CONTENT(tireDot02.getId(), 2L)));

        PurchaseContent purchaseContent = purchase.getContents()
                .stream()
                .filter(content -> content.getTireDot().getId().equals(tireDot02.getId()))
                .findAny()
                .orElseThrow(() -> new RuntimeException("타이어 DOT 가 비정상 생성됨"));
        clear();

        // WHEN
        Purchase updated = purchaseService.update(purchase.getId(),
                PURCHASE(vendor.getId(), LocalDate.now(),
                        PURCHASE_CONTENT(tireDot01.getId(), 1L),
                        PURCHASE_CONTENT(tireDot01.getId(), 3L),
                        PURCHASE_CONTENT(tireDot03.getId(), 3L)));
        clear();

        // THEN
        assertThat(updated.getId()).isEqualTo(purchase.getId());
        assertThat(updated.getContents().size()).isEqualTo(2);
        assertThat(updated.getContents().stream()
                .map(PurchaseContent::getQuantity)
                .reduce(0L, Long::sum)).isEqualTo(7L);
        assertThatThrownBy(() -> purchaseContentService.findById(purchaseContent.getId()))
                .isInstanceOf(NotFoundException.class);
        clear();
    }

    /**
     * 1. 매입 확정 테스트
     * 1-1) 매입의 상태가 확정으로 변경되어야 한다.
     * 1-2) 매입이 확정되면 수정할 수 없다. -> 예외가 발생해야 한다.
     * 1-3) 매입이 확정되면 해당 내용이 재고에 반영되어야 한다.
     * 1-4) 매입을 확정할 때, 재고의 총합이 정확하지 않으면 예외가 발생해야 한다.
     */
    @Test
    @DisplayName("매입 확정")
    void confirm() {
        // GIVEN
        Warehouse warehouse = warehouseService.create(WAREHOUSE("테스트 창고"));
        Vendor vendor = vendorService.create(VENDOR("테스트 매입처"));
        Brand brand = brandService.create(BRAND("테스트 브랜드"));
        Pattern pattern = patternService.create(brand.getId(), PATTERN("테스트 패턴"));
        Tire tire = tireService.create(TIRE("PRODUCT_ID_01", pattern.getId(), 11));
        TireDot tireDot01 = tireDotService.create(tire.getId(), TIRE_DOT("1111", 2000L));
        TireDot tireDot02 = tireDotService.create(tire.getId(), TIRE_DOT("2222", 4000L));

        Purchase purchase = purchaseService.create(PURCHASE(vendor.getId(), LocalDate.now(),
                PURCHASE_CONTENT(tireDot01.getId(), 1L),
                PURCHASE_CONTENT(tireDot02.getId(), 3L)));
        assertThat(purchase.getStatus()).isEqualTo(PurchaseStatus.REQUESTED);

        List<PurchaseContent> contents = new ArrayList<>(purchase.getContents());
        PurchaseContent purchaseContent01 = contents.get(0);
        PurchaseContent purchaseContent02 = contents.get(1);
        clear();

        // WHEN
        Purchase confirmed = purchaseService.confirm(purchase.getId(),
                Arrays.asList(
                        PURCHASE_CONFIRM(purchaseContent01.getId(),
                                STOCK_MODIFY("별칭", warehouse.getId(), 1L, true)),
                        PURCHASE_CONFIRM(purchaseContent02.getId(),
                                STOCK_MODIFY("별칭", warehouse.getId(), 2L, true),
                                STOCK_MODIFY("별칭2", warehouse.getId(), 1L, false))));
        clear();

        // THEN
        assertThat(confirmed.getStatus()).isEqualTo(PurchaseStatus.CONFIRMED);
        assertThat(tireDotService.findById(tireDot01.getId()).getSumOfQuantity()).isEqualTo(1L);
        assertThat(tireDotService.findById(tireDot01.getId()).getStocks().size()).isEqualTo(1);
        assertThat(tireDotService.findById(tireDot02.getId()).getSumOfQuantity()).isEqualTo(3L);
        assertThat(tireDotService.findById(tireDot02.getId()).getStocks().size()).isEqualTo(2);
        clear();
    }

    @Test
    @DisplayName("매입 삭제")
    void removeById() {
        // GIVEN
        Vendor vendor = vendorService.create(VENDOR("테스트 매입처"));
        Brand brand = brandService.create(BRAND("테스트 브랜드"));
        Pattern pattern = patternService.create(brand.getId(), PATTERN("테스트 패턴"));
        Tire tire = tireService.create(TIRE("PRODUCT_ID_01", pattern.getId(), 11));
        TireDot tireDot = tireDotService.create(tire.getId(), TIRE_DOT("1111", 2000L));

        Purchase purchase = purchaseService.create(PURCHASE(vendor.getId(), LocalDate.now(),
                PURCHASE_CONTENT(tireDot.getId(), 1L)));
        assertThat(purchaseService.findAll(null, null).size()).isEqualTo(1);
        clear();

        // WHEN
        purchaseService.removeById(purchase.getId());
        clear();

        // THEN
        assertThatThrownBy(() -> purchaseService.findById(purchase.getId()))
                .isInstanceOf(NotFoundException.class);
        clear();
    }
}