package com.minsoo.co.tireerpserver.service;

import com.minsoo.co.tireerpserver.model.dto.purchase.PurchaseConfirmRequest;
import com.minsoo.co.tireerpserver.model.dto.stock.TireStockResponse;
import com.minsoo.co.tireerpserver.model.entity.entities.management.Brand;
import com.minsoo.co.tireerpserver.model.entity.entities.management.Pattern;
import com.minsoo.co.tireerpserver.model.entity.entities.management.Vendor;
import com.minsoo.co.tireerpserver.model.entity.entities.management.Warehouse;
import com.minsoo.co.tireerpserver.model.entity.entities.purchase.Purchase;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.minsoo.co.tireerpserver.utils.RequestBuilder.*;
import static com.minsoo.co.tireerpserver.utils.RequestBuilder.CREATE_PURCHASE_CONTENT;
import static org.assertj.core.api.Assertions.*;

class StockServiceTest extends ServiceTest {

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

    @BeforeEach
    void setUp() {
        Brand brand = brandService.create(BRAND("테스트 브랜드"));
        Pattern pattern = patternService.create(brand.getId(), PATTERN("테스트 패턴", null));
        Vendor vendor = vendorService.create(VENDOR("테스트 매입처"));
        Warehouse warehouse = warehouseService.create(WAREHOUSE("테스트 창고"));

        Tire tire01 = tireService.create(TIRE("PRODUCT_ID_01", pattern.getId(), 11, null));    // size: 1656011
        Tire tire02 = tireService.create(TIRE("PRODUCT_ID_02", pattern.getId(), 12, null));    // size: 1656012
        TireDot tireDot01 = tireDotService.create(tire01.getId(), TIRE_DOT("1111", 2000L));
        TireDot tireDot02 = tireDotService.create(tire01.getId(), TIRE_DOT("2222", 4000L));
        Purchase purchase = purchaseService.create(CREATE_PURCHASE(vendor.getId(),
                CREATE_PURCHASE_CONTENT(tireDot01.getId(), 1L),
                CREATE_PURCHASE_CONTENT(tireDot01.getId(), 2L)));
        List<PurchaseConfirmRequest> purchaseConfirmRequests = purchase.getPurchaseContents()
                .stream()
                .map(purchaseContent ->
                        CONFIRM_PURCHASE(purchaseContent.getId(),
                                Collections.singletonList(MODIFY_STOCK(null, "별칭", warehouse.getId(), purchaseContent.getQuantity(), true))))
                .collect(Collectors.toList());
        purchaseService.confirm(purchase.getId(), purchaseConfirmRequests);
        clear();
    }

    /**
     * 1. 재고 목록 조회 테스트
     * 1-1) 목록이 정상 조회되어야 한다. (재고가 없어도 타이어는 조회되어야 한다)
     * 1-2) 검색 조건이 포함된 목록만 조회되어야 한다.
     * 1-3) DOT 의 개수를 보여줄 때, 재고가 0개 이상인 DOT 의 개수만 보여줘야 한다.  //TODO
     */
    @Test
    @DisplayName("타이어-재고 목록 조회 테스트")
    void findTireStocksTest() {
        log.debug("목록이 정상 조회되어야 한다.");
        List<TireStockResponse> tireStocks = stockService.findTireStocks(null, null, null, null);
        assertThat(tireStocks.size()).isEqualTo(2);
        clear();

        log.debug("검색 조건이 포함된 목록만 조회되어야 한다.");
        log.debug("사이즈 검색 테스트");
        List<TireStockResponse> sizeTest = stockService.findTireStocks("11", null, null, null);
        assertThat(sizeTest.size()).isEqualTo(1);
        assertThat(sizeTest.get(0).getTire().getProductId()).isEqualTo("PRODUCT_ID_01");
        clear();

        log.debug("패턴 검색 테스트");
        List<TireStockResponse> patternTest = stockService.findTireStocks(null, null, "패턴02", null);
        assertThat(patternTest.size()).isEqualTo(1);
        assertThat(patternTest.get(0).getTire().getProductId()).isEqualTo("PRODUCT_ID_02");
        clear();

        log.debug("상품 ID 검색 테스트");
        List<TireStockResponse> productIdTest = stockService.findTireStocks(null, null, null, "PRODUCT_ID");
        assertThat(productIdTest.size()).isEqualTo(2);
        clear();

        log.debug("브랜드 이름 & 사이즈 검색 테스트");
        List<TireStockResponse> brandNameTest = stockService.findTireStocks("12", "테스트", null, null);
        assertThat(brandNameTest.size()).isEqualTo(1);
        assertThat(brandNameTest.get(0).getTire().getProductId()).isEqualTo("PRODUCT_ID_02");
        clear();
    }

    /**
     * 1. 재고 Lock 수정 테스트
     * 2. 재고 이동 테스트
     * 2-1) 재고가 부족하면 예외가 발생해야 한다.
     * 2-2) 이동하는 목표 대상의 재고 객체가 없으면, 재고 객체를 생성해서 반영해야 한다.
     * 2-3) 이동하는 목표 대상의 재고 객체가 있으면, 해당 재고 객체에 반영해야 한다.
     * 2-4) 양쪽 재고에 이동 갯수가 정상적을 적용되어야 한다.
     */
    @Test
    @DisplayName("재고 수정 테스트")
    void stockUpdateTest() {
    }
}