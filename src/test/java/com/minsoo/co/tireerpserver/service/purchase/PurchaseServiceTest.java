package com.minsoo.co.tireerpserver.service.purchase;

import com.minsoo.co.tireerpserver.api.error.exceptions.AlreadyConfirmedException;
import com.minsoo.co.tireerpserver.api.error.exceptions.BadRequestException;
import com.minsoo.co.tireerpserver.api.error.exceptions.NotFoundException;
import com.minsoo.co.tireerpserver.model.code.PurchaseStatus;
import com.minsoo.co.tireerpserver.model.dto.purchase.CreatePurchaseRequest;
import com.minsoo.co.tireerpserver.model.dto.purchase.UpdatePurchaseContentRequest;
import com.minsoo.co.tireerpserver.model.entity.entities.management.Brand;
import com.minsoo.co.tireerpserver.model.entity.entities.management.Pattern;
import com.minsoo.co.tireerpserver.model.entity.entities.management.Vendor;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Arrays;
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
    PurchaseContentService purchaseContentService;

    @Autowired
    StockService stockService;

    @BeforeEach
    void setUp() {
        vendorService.create(VENDOR("테스트 매입처"));

        Brand brand = brandService.create(BRAND("테스트 브랜드"));
        Pattern pattern = patternService.create(brand.getId(), PATTERN("테스트 패턴"));
        Tire tire = tireService.create(TIRE("PRODUCT_ID_01", pattern.getId(), 11));

        tireDotService.create(tire.getId(), TIRE_DOT("1111", 2000L));
        tireDotService.create(tire.getId(), TIRE_DOT("2222", 4000L));
        tireDotService.create(tire.getId(), TIRE_DOT("3333", 6000L));
    }

    /**
     * 1. 매입이 정상적으로 생성되어야 한다.
     * 2. 매입 항목의 tire-dot 가 중복으로 요청 되는 경우 합쳐서 저장한다.
     */
    @Test
    @DisplayName("매입 생성")
    void create() {
        // GIVEN
        Vendor vendor = vendorService.findById(1L);
        TireDot tireDot01 = tireDotService.findById(1L);
        TireDot tireDot02 = tireDotService.findById(2L);

        CreatePurchaseRequest createPurchaseRequest = PURCHASE_CREATE(
                vendor.getId(), LocalDate.now(),
                PURCHASE_CONTENT_CREATE(tireDot01.getId(), 1L),
                PURCHASE_CONTENT_CREATE(tireDot02.getId(), 2L),
                PURCHASE_CONTENT_CREATE(tireDot01.getId(), 1L));

        // WHEN
        Purchase purchase = purchaseService.create(createPurchaseRequest);

        // THEN
        assertThat(purchase.getContents().size()).isEqualTo(2);
        assertThat(purchase.getContents().stream()
                .map(PurchaseContent::getQuantity)
                .reduce(0L, Long::sum)).isEqualTo(4);
        assertThat(purchase.getVendor().getId()).isEqualTo(vendor.getId());
        assertThat(purchase.getPurchaseDate()).isEqualTo(LocalDate.now());
    }

    /**
     * 1. 매입 일자 조건으로 검색했을 때, 정상 검색되어야 한다.
     */
    @Test
    @DisplayName("매입 목록 조회")
    void findAll() {
        // GIVEN
        Vendor vendor = vendorService.findById(1L);
        TireDot tireDot = tireDotService.findById(1L);

        // WHEN
        purchaseService.create(PURCHASE_CREATE(vendor.getId(), LocalDate.of(2021, 2, 27), PURCHASE_CONTENT_CREATE(tireDot.getId(), 1L)));
        purchaseService.create(PURCHASE_CREATE(vendor.getId(), LocalDate.of(2021, 2, 28), PURCHASE_CONTENT_CREATE(tireDot.getId(), 1L)));
        purchaseService.create(PURCHASE_CREATE(vendor.getId(), LocalDate.of(2021, 3, 1), PURCHASE_CONTENT_CREATE(tireDot.getId(), 1L)));

        // THEN
        assertThat(purchaseService.findAll(null, null).size()).isEqualTo(3);
        assertThat(purchaseService.findAll(LocalDate.of(2021, 2, 28), null).size()).isEqualTo(2);
        assertThat(purchaseService.findAll(LocalDate.of(2021, 3, 1), null).size()).isEqualTo(1);
        assertThat(purchaseService.findAll(null, LocalDate.of(2021, 2, 28)).size()).isEqualTo(2);
        assertThat(purchaseService.findAll(LocalDate.of(2021, 2, 27),
                LocalDate.of(2021, 2, 28)).size()).isEqualTo(2);
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
        Purchase purchase = purchaseService.create(PURCHASE_CREATE(1L, LocalDate.now(),
                PURCHASE_CONTENT_CREATE(1L, 1L),
                PURCHASE_CONTENT_CREATE(2L, 2L)));

        // WHEN
        Purchase updated = purchaseService.update(purchase.getId(),
                PURCHASE_UPDATE(1L,
                        PURCHASE_CONTENT_UPDATE(2L, 1L, 1L),
                        PURCHASE_CONTENT_UPDATE(null, 1L, 3L),
                        PURCHASE_CONTENT_UPDATE(null, 3L, 3L)));

        // THEN
        assertThat(updated.getId()).isEqualTo(purchase.getId());
        assertThat(updated.getContents().size()).isEqualTo(2);
        assertThat(updated.getContents().stream()
                .map(PurchaseContent::getQuantity)
                .reduce(0L, Long::sum)).isEqualTo(7L);
        assertThatThrownBy(() -> purchaseContentService.findById(1L)).isInstanceOf(NotFoundException.class);
    }

    /**
     * 1. 매입 확정 테스트
     * 1-1) 매입의 상태가 확정으로 변경되어야 한다.
     * 1-2) 매입이 확정되면 수정할 수 없다. -> 예외가 발생해야 한다.
     * 1-3) 매입이 확정되면 해당 내용이 재고에 반영되어야 한다.
     * 1-4) 매입을 확정할 때, 재고의 총합이 이상하면 안 된다.
     */
    @Test
    @DisplayName("매입 확정")
    void confirm() {
        // GIVEN
        Purchase purchase = purchaseService.create(PURCHASE_CREATE(1L, LocalDate.now(),
                PURCHASE_CONTENT_CREATE(1L, 1L),
                PURCHASE_CONTENT_CREATE(2L, 2L)));
        assertThat(purchase.getStatus()).isEqualTo(PurchaseStatus.REQUESTED);

        List<PurchaseConfirmRequest> confirmRequests = Arrays.asList(
                PURCHASE_CONFIRM(1L,
                        STOCK_MODIFY(null, "별칭", 1L, 1L, true)),
                PURCHASE_CONFIRM(2L,
                        STOCK_MODIFY(null, "별칭", 1L, 2L, true),
                        STOCK_MODIFY(null, "별칭2", 1L, 1L, false)));

        // WHEN
        purchaseService.confirm(purchase.getId(), confirmRequests);

        // THEN
        assertThat(purchaseService.findById(purchase.getId()).getStatus()).isEqualTo(PurchaseStatus.CONFIRMED);
        assertThat(tireDotService.findById())
        clear();

        log.debug("매입이 확정되면 재고에 반영되어야 한다.");
        TireDot foundDot01 = tireDotService.findByIds(tire.getId(), tireDot01.getId());
        assertThat(foundDot01.getSumOfQuantity()).isEqualTo(1L);
        clear();

        log.debug("매입 확정 시 수량이 다르면 오류가 발생해야 한다.");
        Purchase failed = purchaseService.create(CREATE_PURCHASE(vendor.getId(), LocalDate.now(),
                PURCHASE_CONTENT_CREATE(tireDot01.getId(), 1L)));
        List<PurchaseConfirmRequest> invalidRequest = failed.getContents()
                .stream()
                .map(purchaseContent ->
                        CONFIRM_PURCHASE(purchaseContent.getId(),
                                Collections.singletonList(STOCK_MODIFY(null, "별칭", warehouse.getId(), purchaseContent.getQuantity() + 99, true))))
                .collect(Collectors.toList());

        assertThatThrownBy(() -> purchaseService.confirm(failed.getId(), invalidRequest))
                .isInstanceOf(BadRequestException.class);
        clear();

        log.debug("매입이 확정되면 수정할 수 없다.");
        List<UpdatePurchaseContentRequest> contents = purchase.getContents()
                .stream()
                .map(purchaseContent ->
                        PURCHASE_CONTENT_UPDATE(purchaseContent.getId(), purchaseContent.getTireDot().getId(), 3L))
                .collect(Collectors.toList());
        assertThatThrownBy(() -> purchaseService.update(purchase.getId(), UPDATE_PURCHASE(vendor.getId(), contents)))
                .isInstanceOf(AlreadyConfirmedException.class);
    }

    @Test
    @DisplayName("매입 삭제")
    void removeById() {
        // GIVEN
        Purchase purchase = purchaseService.create(PURCHASE_CREATE(1L, LocalDate.now(),
                PURCHASE_CONTENT_CREATE(1L, 1L)));
        assertThat(purchase.getVendor().getId()).isEqualTo(1L);

        // WHEN
        purchaseService.removeById(purchase.getId());

        // THEN
        assertThatThrownBy(() -> purchaseService.findById(purchase.getId()))
                .isInstanceOf(NotFoundException.class);
    }
}