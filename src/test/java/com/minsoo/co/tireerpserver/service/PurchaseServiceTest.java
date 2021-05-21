//package com.minsoo.co.tireerpserver.service;
//
//import com.minsoo.co.tireerpserver.api.error.exceptions.AlreadyConfirmedException;
//import com.minsoo.co.tireerpserver.api.error.exceptions.NotFoundException;
//import com.minsoo.co.tireerpserver.model.code.PurchaseStatus;
//import com.minsoo.co.tireerpserver.model.dto.management.vendor.VendorResponse;
//import com.minsoo.co.tireerpserver.model.dto.management.warehouse.WarehouseResponse;
//import com.minsoo.co.tireerpserver.model.dto.purchase.PurchaseSimpleResponse;
//import com.minsoo.co.tireerpserver.model.dto.stock.StockSimpleResponse;
//import com.minsoo.co.tireerpserver.model.dto.tire.tire.TireResponse;
//import com.minsoo.co.tireerpserver.model.dto.tire.dot.TireDotSimpleResponse;
//import com.minsoo.co.tireerpserver.model.entity.entities.management.Brand;
//import com.minsoo.co.tireerpserver.model.entity.entities.tire.TireDot;
//import com.minsoo.co.tireerpserver.service.management.BrandService;
//import com.minsoo.co.tireerpserver.service.purchase.PurchaseService;
//import com.minsoo.co.tireerpserver.service.stock.StockService;
//import com.minsoo.co.tireerpserver.service.tire.TireDotService;
//import com.minsoo.co.tireerpserver.service.tire.TireService;
//import com.minsoo.co.tireerpserver.service.management.VendorService;
//import com.minsoo.co.tireerpserver.service.management.WarehouseService;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.time.LocalDate;
//import java.util.List;
//
//import static com.minsoo.co.tireerpserver.utils.RequestBuilder.*;
//import static org.assertj.core.api.Assertions.*;
//
//class PurchaseServiceTest extends ServiceTest {
//
//    @Autowired
//    BrandService brandService;
//
//    @Autowired
//    VendorService vendorService;
//
//    @Autowired
//    WarehouseService warehouseService;
//
//    @Autowired
//    TireService tireService;
//
//    @Autowired
//    TireDotService tireDotService;
//
//    @Autowired
//    PurchaseService purchaseService;
//
//    @Autowired
//    StockService stockService;
//
//    /**
//     * 1. 매입 생성 테스트
//     * 1-1) 매입 생성 요청 중 이미 존재하는 dot 에 대한 요청인 경우, dot 를 새로 생성하지 않는다.
//     * 1-2) 존재하지 않는 dot 에 대한 요청인 경우, dot 를 새로 생성한다.
//     * 2. 매입 수정/삭제 테스트
//     * 2-1) 매입이 확정되기 전에는 매입 내용을 수정할 수 있다.
//     * 2-2) 매입이 확정되기 전에는 매입 내용을 삭제할 수 있다.
//     */
//    @Test
//    @DisplayName("매입 생성 & 수정 & 삭제 테스트")
//    void purchaseTest() {
//        log.info("초기 데이터 생성");
//        Brand brand = brandService.create(BRAND("테스트 브랜드"));
//        VendorResponse vendor = vendorService.create(VENDOR("테스트 매입처"));
//        WarehouseResponse warehouse = warehouseService.create(WAREHOUSE("테스트 창고"));
//        TireResponse tire = tireService.create(TIRE(brand.getId(), "PRODUCT_ID_01", 11, "테스트 타이어"));
//        clear();
//
//        log.info("1. 매입 생성 테스트");
//        log.debug("매입이 이루어지기 전에는 dot 가 없다.");
//        assertThat(tireDotService.findAllByTireId(tire.getTireId()).size()).isEqualTo(0);
//        clear();
//
//        log.debug("매입 생성 1");
//        List<PurchaseSimpleResponse> purchaseList01 = purchaseService.create(CREATE_PURCHASE(vendor.getVendorId(),
//                CREATE_PURCHASE_CONTENT(tire.getTireId(), "dot01", warehouse.getWarehouseId(), 1L),
//                CREATE_PURCHASE_CONTENT(tire.getTireId(), "dot02", warehouse.getWarehouseId(), 2L)));
//        clear();
//
//        log.debug("매입 생성 검증");
//        PurchaseSimpleResponse purchase01 = purchaseList01.get(0);
//        PurchaseSimpleResponse purchase02 = purchaseList01.get(1);
//
//        assertThat(purchaseList01.size()).isEqualTo(2);
//        assertThat(purchase01.getVendorId()).isEqualTo(vendor.getVendorId());
//        assertThat(purchase01.getWarehouseId()).isEqualTo(warehouse.getWarehouseId());
//        assertThat(purchase01.getPurchaseDate()).isEqualTo(LocalDate.now());
//
//        log.debug("매입이 일어난 후 dot 가 생성되어야 한다.");
//        assertThat(tireDotService.findAllByTireId(tire.getTireId()).size()).isEqualTo(2);
//        clear();
//
//        log.debug("dot 생성 검증");
//        TireDot dot01 = tireDotService.findByIds(tire.getTireId(), purchase01.getTireDot().getTireDotId());
//        TireDot dot02 = tireDotService.findByIds(tire.getTireId(), purchase02.getTireDot().getTireDotId());
//        assertThat(dot01.getDot()).isEqualTo("dot01");
//        assertThat(dot02.getDot()).isEqualTo("dot02");
//        assertThat(dot01.getId()).isNotEqualTo(dot02.getId());
//        clear();
//
//        log.debug("매입 생성 2");
//        List<PurchaseSimpleResponse> purchaseList02 = purchaseService.create(CREATE_PURCHASE(vendor.getVendorId(),
//                CREATE_PURCHASE_CONTENT(tire.getTireId(), "dot01", warehouse.getWarehouseId(), 1L)));
//        clear();
//
//        log.debug("dot 는 추가로 생성되지 않아야 한다.");
//        assertThat(tireDotService.findAllByTireId(tire.getTireId()).size()).isEqualTo(2);
//        assertThat(purchaseList02.get(0).getTireDot().getTireDotId()).isEqualTo(dot01.getTireDotId());
//        clear();
//
//        log.info("2. 매입 수정/삭제 테스트");
//        log.debug("수정 테스트");
//        PurchaseSimpleResponse updatedPurchase01 = purchaseService.update(purchase01.getPurchaseId(), UPDATE_PURCHASE(vendor.getVendorId(), tire.getTireId(), "dot01", warehouse.getWarehouseId(), 50L));
//        assertThat(updatedPurchase01.getPurchaseId()).isEqualTo(purchase01.getPurchaseId());
//        assertThat(updatedPurchase01.getQuantity()).isEqualTo(50L);
//        clear();
//
//        log.debug("삭제 테스트");
//        purchaseService.removeById(purchase01.getPurchaseId());
//        assertThatThrownBy(() -> purchaseService.findById(purchase01.getPurchaseId()))
//                .isInstanceOf(NotFoundException.class);
//        clear();
//    }
//
//    /**
//     * 1. 매입 확정 테스트
//     * 1-1) 매입의 상태가 확정으로 변경되어야 한다.
//     * 1-2) 매입이 확정되면 수정/삭제할 수 없다. -> 예외가 발생해야 한다.
//     * 1-3) 매입이 확정되면 해당 내용이 재고에 반영되어야 한다. -> 재고 객체가 없으면 신규 생성, 있으면 수량만 수정
//     */
//    @Test
//    @DisplayName("매입 확정 테스트")
//    public void purchaseConfirmTest() {
//        log.info("초기 데이터 생성");
//        Brand brand = brandService.create(BRAND("테스트 브랜드"));
//        VendorResponse vendor = vendorService.create(VENDOR("테스트 매입처"));
//        WarehouseResponse warehouse = warehouseService.create(WAREHOUSE("테스트 창고"));
//        TireResponse tire = tireService.create(TIRE(brand.getId(), "PRODUCT_ID_01", 11, "테스트 타이어"));
//        List<PurchaseSimpleResponse> purchases = purchaseService.create(CREATE_PURCHASE(vendor.getVendorId(),
//                CREATE_PURCHASE_CONTENT(tire.getTireId(), "dot01", warehouse.getWarehouseId(), 1L),
//                CREATE_PURCHASE_CONTENT(tire.getTireId(), "dot01", warehouse.getWarehouseId(), 2L)));
//        clear();
//
//        log.info("매입 확정 테스트");
//        purchaseService.confirm(purchases.get(0).getPurchaseId());
//        clear();
//
//        log.debug("매입의 상태가 확정으로 변경되어야 한다.");
//        assertThat(purchases.get(0).getStatus()).isEqualTo(PurchaseStatus.REQUESTED);
//        assertThat(purchaseService.findById(purchases.get(0).getPurchaseId()).getStatus()).isEqualTo(PurchaseStatus.CONFIRMED);
//        clear();
//
//        log.debug("매입이 확정되면 재고에 반영되어야 한다. -> 재고 객체 신규 생성");
//        List<StockSimpleResponse> stocks1 = stockService.findAllByTireId(tire.getTireId());
//        assertThat(stocks1.size()).isEqualTo(1);
//        assertThat(stocks1.get(0).getQuantity()).isEqualTo(1L);
//        clear();
//
//        log.debug("동일한 재고가 추가되면 수량만 변경된다. -> 기존 재고 객체에 수량만 추가");
//        purchaseService.confirm(purchases.get(1).getPurchaseId());
//        clear();
//
//        List<StockSimpleResponse> stocks2 = stockService.findAllByTireId(tire.getTireId());
//        assertThat(stocks2.size()).isEqualTo(1);
//        assertThat(stocks2.get(0).getQuantity()).isEqualTo(3L);
//        clear();
//
//        log.debug("매입이 확정되면 수정/삭제할 수 없다.");
//        assertThatThrownBy(() -> purchaseService.update(purchases.get(0).getPurchaseId(), UPDATE_PURCHASE(vendor.getVendorId(), tire.getTireId(), "dot01", warehouse.getWarehouseId(), 50L)))
//                .isInstanceOf(AlreadyConfirmedException.class);
//        assertThatThrownBy(() -> purchaseService.removeById(purchases.get(1).getPurchaseId()))
//                .isInstanceOf(AlreadyConfirmedException.class);
//        clear();
//    }
//}