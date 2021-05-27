package com.minsoo.co.tireerpserver.service.stock;

import com.minsoo.co.tireerpserver.model.dto.purchase.content.PurchaseContentConfirmRequest;
import com.minsoo.co.tireerpserver.model.dto.stock.TireStockResponse;
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
import com.minsoo.co.tireerpserver.service.purchase.PurchaseService;
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
import static com.minsoo.co.tireerpserver.utils.RequestBuilder.PURCHASE_CONTENT;
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
        Pattern pattern01 = patternService.create(brand.getId(), PATTERN("패턴01"));
        Pattern pattern02 = patternService.create(brand.getId(), PATTERN("패턴02"));
        Vendor vendor = vendorService.create(VENDOR("테스트 매입처"));
        Warehouse warehouse = warehouseService.create(WAREHOUSE("테스트 창고"));

        Tire tire01 = tireService.create(TIRE("PRODUCT_ID_01", pattern01.getId(), 11));    // size: 1656011
        Tire tire02 = tireService.create(TIRE("PRODUCT_ID_02", pattern02.getId(), 12));    // size: 1656012
        TireDot tireDot01 = tireDotService.create(tire01.getId(), TIRE_DOT("1111", 2000L));
        TireDot tireDot02 = tireDotService.create(tire01.getId(), TIRE_DOT("2222", 4000L));
        TireDot tireDot03 = tireDotService.create(tire02.getId(), TIRE_DOT("3333", 6000L));
        Purchase purchase = purchaseService.create(PURCHASE(vendor.getId(), LocalDate.now(),
                PURCHASE_CONTENT(tireDot01.getId(), 1L),
                PURCHASE_CONTENT(tireDot02.getId(), 2L),
                PURCHASE_CONTENT(tireDot03.getId(), 3L)));
        PurchaseContent content01 = purchase.getContents()
                .stream()
                .filter(content -> content.getTireDot().getId().equals(tireDot01.getId()))
                .findAny().orElseThrow(RuntimeException::new);
        PurchaseContent content02 = purchase.getContents()
                .stream()
                .filter(content -> content.getTireDot().getId().equals(tireDot02.getId()))
                .findAny().orElseThrow(RuntimeException::new);
        PurchaseContent content03 = purchase.getContents()
                .stream()
                .filter(content -> content.getTireDot().getId().equals(tireDot03.getId()))
                .findAny().orElseThrow(RuntimeException::new);

        purchaseService.confirm(purchase.getId(),
                Arrays.asList(
                        PURCHASE_CONFIRM(content01.getId(), STOCK_MODIFY("별칭", warehouse.getId(), 1L, true)),
                        PURCHASE_CONFIRM(content02.getId(), STOCK_MODIFY("별칭", warehouse.getId(), 2L, false)),
                        PURCHASE_CONFIRM(content03.getId(),
                                STOCK_MODIFY("별칭1", warehouse.getId(), 1L, true),
                                STOCK_MODIFY("별칭2", warehouse.getId(), 2L, false))));
    }

    /**
     * 1. 재고 목록 조회 테스트
     * 1-1) 목록이 정상 조회되어야 한다. (재고가 없어도 타이어는 조회되어야 한다)
     * 1-2) 검색 조건이 포함된 목록만 조회되어야 한다.
     * 1-3) DOT 의 개수를 보여줄 때, 재고가 0개 이상인 DOT 의 개수만 보여줘야 한다.  //TODO
     * 1-4) 공개 재고는 공개된 재고의 합만 보여줘야 한다. //TODO
     */
    @Test
    @DisplayName("타이어-재고 목록 조회 테스트")
    void findTireStocksTest() {
        log.debug("목록 조회 테스트");
        List<TireStockResponse> tireStocks = stockService.findTireStocks(null, null, null, null);
        assertThat(tireStocks.size()).isEqualTo(2);
        clear();

        log.debug("사이즈 검색 테스트");
        List<TireStockResponse> sizeTest = stockService.findTireStocks("11", null, null, null);
        assertThat(sizeTest.size()).isEqualTo(1);
        assertThat(sizeTest.get(0).getTire().getProductId()).isEqualTo("PRODUCT_ID_01");
        assertThat(sizeTest.get(0).getSumOfStock()).isEqualTo(3L);
        assertThat(sizeTest.get(0).getSumOfOpenedStock()).isEqualTo(2L);
        assertThat(sizeTest.get(0).getNumberOfActiveDot()).isEqualTo(2L);
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
}