package com.minsoo.co.tireerpserver.service;

import com.minsoo.co.tireerpserver.api.error.exceptions.AlreadyConfirmedException;
import com.minsoo.co.tireerpserver.api.error.exceptions.NotFoundException;
import com.minsoo.co.tireerpserver.model.code.PurchaseStatus;
import com.minsoo.co.tireerpserver.model.dto.purchase.PurchaseConfirmRequest;
import com.minsoo.co.tireerpserver.model.dto.purchase.UpdatePurchaseContentRequest;
import com.minsoo.co.tireerpserver.model.entity.entities.management.Brand;
import com.minsoo.co.tireerpserver.model.entity.entities.management.Pattern;
import com.minsoo.co.tireerpserver.model.entity.entities.management.Vendor;
import com.minsoo.co.tireerpserver.model.entity.entities.management.Warehouse;
import com.minsoo.co.tireerpserver.model.entity.entities.purchase.Purchase;
import com.minsoo.co.tireerpserver.model.entity.entities.purchase.PurchaseContent;
import com.minsoo.co.tireerpserver.model.entity.entities.tire.Tire;
import com.minsoo.co.tireerpserver.model.entity.entities.tire.TireDot;
import com.minsoo.co.tireerpserver.service.management.BrandService;
import com.minsoo.co.tireerpserver.service.management.PatternService;
import com.minsoo.co.tireerpserver.service.purchase.PurchaseService;
import com.minsoo.co.tireerpserver.service.stock.StockService;
import com.minsoo.co.tireerpserver.service.tire.TireDotService;
import com.minsoo.co.tireerpserver.service.tire.TireService;
import com.minsoo.co.tireerpserver.service.management.VendorService;
import com.minsoo.co.tireerpserver.service.management.WarehouseService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    StockService stockService;

    /**
     * 1. 매입 생성 테스트
     * 1-1) 매입이 정상적으로 생성되어야 한다.
     * 2. 매입 수정/삭제 테스트
     * 2-1) 매입이 확정되기 전에는 매입 내용을 수정할 수 있다.
     * 2-2) 확정 여부와 무관하게 매입 내용을 삭제할 수 있다.
     */
    @Test
    @DisplayName("매입 생성 & 수정 & 삭제 테스트")
    void purchaseTest() {
        log.info("초기 데이터 생성");
        Brand brand = brandService.create(BRAND("테스트 브랜드"));
        Pattern pattern = patternService.create(brand.getId(), PATTERN("테스트 패턴", null));
        Vendor vendor = vendorService.create(VENDOR("테스트 매입처"));
        Tire tire = tireService.create(TIRE("PRODUCT_ID_01", pattern.getId(), 11, null));
        TireDot tireDot01 = tireDotService.create(tire.getId(), TIRE_DOT("1111", 2000L));
        TireDot tireDot02 = tireDotService.create(tire.getId(), TIRE_DOT("2222", 4000L));
        clear();

        log.info("1. 매입 생성 테스트");
        log.debug("매입 생성 1");
        Purchase purchase = purchaseService.create(CREATE_PURCHASE(vendor.getId(),
                CREATE_PURCHASE_CONTENT(tireDot01.getId(), 1L),
                CREATE_PURCHASE_CONTENT(tireDot02.getId(), 2L)));
        clear();

        log.debug("매입 생성 검증");
        assertThat(purchase.getPurchaseContents().size()).isEqualTo(2);
        assertThat(purchase.getVendor().getId()).isEqualTo(vendor.getId());
        assertThat(purchase.getPurchaseDate()).isEqualTo(LocalDate.now());

        log.info("2. 매입 수정/삭제 테스트");
        log.debug("수정 테스트");
        List<UpdatePurchaseContentRequest> contents = purchase.getPurchaseContents()
                .stream()
                .map(purchaseContent ->
                        UPDATE_PURCHASE_CONTENT(purchaseContent.getId(), purchaseContent.getTireDot().getId(), 3L))
                .collect(Collectors.toList());
        Purchase updated = purchaseService.update(purchase.getId(), UPDATE_PURCHASE(vendor.getId(), contents));
        assertThat(updated.getId()).isEqualTo(purchase.getId());
        assertThat(updated.getPurchaseContents().stream()
                .map(PurchaseContent::getQuantity)
                .reduce(0L, Long::sum)).isEqualTo(6L);
        clear();

        log.debug("삭제 테스트");
        purchaseService.removeById(purchase.getId());
        assertThatThrownBy(() -> purchaseService.findById(purchase.getId()))
                .isInstanceOf(NotFoundException.class);
        clear();
    }

    /**
     * 1. 매입 확정 테스트
     * 1-1) 매입의 상태가 확정으로 변경되어야 한다.
     * 1-2) 매입이 확정되면 수정할 수 없다. -> 예외가 발생해야 한다.
     * 1-3) 매입이 확정되면 해당 내용이 재고에 반영되어야 한다.
     * 1-4) 매입을 확정할 때, 재고의 총합이 이상하면 안 된다.
     */
    @Test
    @DisplayName("매입 확정 테스트")
    public void purchaseConfirmTest() {
        log.info("초기 데이터 생성");
        Brand brand = brandService.create(BRAND("테스트 브랜드"));
        Pattern pattern = patternService.create(brand.getId(), PATTERN("테스트 패턴", null));
        Vendor vendor = vendorService.create(VENDOR("테스트 매입처"));
        Warehouse warehouse = warehouseService.create(WAREHOUSE("테스트 창고"));
        Tire tire = tireService.create(TIRE("PRODUCT_ID_01", pattern.getId(), 11, null));
        TireDot tireDot01 = tireDotService.create(tire.getId(), TIRE_DOT("1111", 2000L));
        Purchase purchase = purchaseService.create(CREATE_PURCHASE(vendor.getId(),
                CREATE_PURCHASE_CONTENT(tireDot01.getId(), 1L)));
        clear();

        log.info("매입 확정 테스트");
        List<PurchaseConfirmRequest> purchaseConfirmRequests = purchase.getPurchaseContents()
                .stream()
                .map(purchaseContent ->
                        CONFIRM_PURCHASE(purchaseContent.getId(),
                                Collections.singletonList(MODIFY_STOCK(null, warehouse.getId(), purchaseContent.getQuantity(), true))))
                .collect(Collectors.toList());
        purchaseService.confirm(purchase.getId(), purchaseConfirmRequests);
        clear();

        log.debug("매입의 상태가 확정으로 변경되어야 한다.");
        assertThat(purchase.getStatus()).isEqualTo(PurchaseStatus.REQUESTED);
        assertThat(purchaseService.findById(purchase.getId()).getStatus()).isEqualTo(PurchaseStatus.CONFIRMED);
        clear();

        log.debug("매입이 확정되면 재고에 반영되어야 한다.");
        TireDot foundDot01 = tireDotService.findByIds(tire.getId(), tireDot01.getId());
        assertThat(foundDot01.getSumOfQuantity()).isEqualTo(1L);
        clear();

        log.debug("동일한 재고가 추가되면 수량만 변경된다. -> 기존 재고 객체에 수량만 추가");


        log.debug("매입이 확정되면 수정할 수 없다.");
        List<UpdatePurchaseContentRequest> contents = purchase.getPurchaseContents()
                .stream()
                .map(purchaseContent ->
                        UPDATE_PURCHASE_CONTENT(purchaseContent.getId(), purchaseContent.getTireDot().getId(), 3L))
                .collect(Collectors.toList());
        assertThatThrownBy(() -> purchaseService.update(purchase.getId(), UPDATE_PURCHASE(vendor.getId(), contents)))
                .isInstanceOf(AlreadyConfirmedException.class);
        clear();
    }
}