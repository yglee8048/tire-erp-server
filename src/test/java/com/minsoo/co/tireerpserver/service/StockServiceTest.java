package com.minsoo.co.tireerpserver.service;

import com.minsoo.co.tireerpserver.api.error.errors.NotEnoughStockException;
import com.minsoo.co.tireerpserver.model.dto.management.brand.BrandResponse;
import com.minsoo.co.tireerpserver.model.dto.management.vendor.VendorResponse;
import com.minsoo.co.tireerpserver.model.dto.management.warehouse.WarehouseResponse;
import com.minsoo.co.tireerpserver.model.dto.purchase.PurchaseSimpleResponse;
import com.minsoo.co.tireerpserver.model.dto.stock.StockResponse;
import com.minsoo.co.tireerpserver.model.dto.stock.TireStockResponse;
import com.minsoo.co.tireerpserver.model.dto.tire.TireResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.minsoo.co.tireerpserver.utils.RequestBuilder.*;
import static com.minsoo.co.tireerpserver.utils.RequestBuilder.CREATE_PURCHASE_CONTENT;
import static org.assertj.core.api.Assertions.*;

class StockServiceTest extends ServiceTest {

    @Autowired
    BrandService brandService;

    @Autowired
    VendorService vendorService;

    @Autowired
    WarehouseService warehouseService;

    @Autowired
    TireService tireService;

    @Autowired
    PurchaseService purchaseService;

    @Autowired
    StockService stockService;

    @BeforeEach
    void setUp() {
        log.info("초기 데이터 생성");
        BrandResponse brand = brandService.create(BRAND("테스트 브랜드"));
        VendorResponse vendor = vendorService.create(VENDOR("테스트 매입처"));
        WarehouseResponse warehouse = warehouseService.create(WAREHOUSE("테스트 창고"));
        TireResponse tire01 = tireService.create(TIRE(brand.getBrandId(), "PRODUCT_ID_01", 11, "패턴01"));    // size: 1656011
        TireResponse tire02 = tireService.create(TIRE(brand.getBrandId(), "PRODUCT_ID_02", 12, "패턴02"));    // size: 1656012
        List<PurchaseSimpleResponse> purchases = purchaseService.create(CREATE_PURCHASE(vendor.getVendorId(),
                CREATE_PURCHASE_CONTENT(tire01.getTireId(), "dot01", warehouse.getWarehouseId(), 1L),
                CREATE_PURCHASE_CONTENT(tire02.getTireId(), "dot02", warehouse.getWarehouseId(), 2L)));
        purchases.forEach(purchase -> purchaseService.confirm(purchase.getPurchaseId()));
        clear();
    }

    /**
     * 1. 재고 목록 조회 테스트
     * 1-1) 목록이 정상 조회되어야 한다.
     * 1-2) 검색 조건이 포함된 목록만 조회되어야 한다.
     * 1-3) DOT 의 개수를 보여줄 때, 재고가 0개 이상인 DOT 의 개수만 보여줘야 한다.  //TODO
     */
    @Test
    @DisplayName("타이어 재고 목록 조회 테스트")
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
        List<StockResponse> stocks = stockService.findAll();

        log.info("재고 Lock 수정 테스트");
        stockService.updateStockLock(stocks.get(0).getStockId(), UPDATE_STOCK_LOCK(false));
        assertThat(stocks.get(0).isLock()).isEqualTo(true);
        assertThat(stockService.findById(stocks.get(0).getStockId()).isLock()).isEqualTo(false);
        clear();

        log.info("재고 이동 테스트");
        WarehouseResponse toWarehouse = warehouseService.create(WAREHOUSE("이동 대상 창고"));

        log.debug("재고가 부족하면 예외가 발생해야 한다.");
        assertThatThrownBy(() -> stockService.moveStock(stocks.get(0).getStockId(), MOVE_STOCK(toWarehouse.getWarehouseId(), 3L)))
                .isInstanceOf(NotEnoughStockException.class);
        clear();

        log.debug("이동하려는 목표 대상의 재고 객체가 없으면, 재고 객체를 생성해서 반영해야 한다.");
        stockService.moveStock(stocks.get(1).getStockId(), MOVE_STOCK(toWarehouse.getWarehouseId(), 1L));
        assertThat(stocks.size()).isEqualTo(2);
        assertThat(stockService.findAll().size()).isEqualTo(3);
        clear();

        log.debug("이동하는 목표 대상의 재고 객체가 있으면, 해당 재고 객체에 반영해야 한다.");
        stockService.moveStock(stocks.get(1).getStockId(), MOVE_STOCK(toWarehouse.getWarehouseId(), 1L));
        assertThat(stockService.findAll().size()).isEqualTo(3);
        clear();

        log.debug("양쪽 재고에 이동 갯수가 정상적을 적용되어야 한다.");
        List<StockResponse> afterStocks = stockService.findAll();
        assertThat(stockService.findById(afterStocks.get(1).getStockId()).getQuantity()).isEqualTo(0L);
        assertThat(stockService.findById(afterStocks.get(2).getStockId()).getQuantity()).isEqualTo(2L);
        clear();
    }
}