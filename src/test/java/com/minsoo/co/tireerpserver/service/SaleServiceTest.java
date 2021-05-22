//package com.minsoo.co.tireerpserver.service;
//
//import com.minsoo.co.tireerpserver.model.dto.account.customer.CustomerResponse;
//import com.minsoo.co.tireerpserver.model.dto.management.vendor.VendorResponse;
//import com.minsoo.co.tireerpserver.model.dto.management.warehouse.WarehouseResponse;
//import com.minsoo.co.tireerpserver.model.dto.sale.SaleSimpleResponse;
//import com.minsoo.co.tireerpserver.model.dto.stock.StockResponse;
//import com.minsoo.co.tireerpserver.model.dto.tire.tire.TireResponse;
//import com.minsoo.co.tireerpserver.model.entity.entities.management.Brand;
//import com.minsoo.co.tireerpserver.service.account.CustomerService;
//import com.minsoo.co.tireerpserver.service.management.BrandService;
//import com.minsoo.co.tireerpserver.service.purchase.PurchaseService;
//import com.minsoo.co.tireerpserver.service.sale.SaleService;
//import com.minsoo.co.tireerpserver.service.stock.StockService;
//import com.minsoo.co.tireerpserver.service.tire.TireService;
//import com.minsoo.co.tireerpserver.service.management.VendorService;
//import com.minsoo.co.tireerpserver.service.management.WarehouseService;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.List;
//
//import static com.minsoo.co.tireerpserver.utils.RequestBuilder.*;
//import static com.minsoo.co.tireerpserver.utils.RequestBuilder.CREATE_PURCHASE_CONTENT;
//import static org.junit.jupiter.api.Assertions.*;
//
//class SaleServiceTest extends ServiceTest {
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
//    PurchaseService purchaseService;
//
//    @Autowired
//    StockService stockService;
//
//    @Autowired
//    CustomerService customerService;
//
//    @Autowired
//    SaleService saleService;
//
//    /**
//     * 매출 생성 테스트
//     * 1) 매출이 정상적으로 생성되어야 한다.
//     */
//    @Test
//    @DisplayName("매출 생성 테스트")
//    void createSaleTest() {
//        log.info("초기 데이터 생성");
//        Brand brand = brandService.create(BRAND("테스트 브랜드"));
//        VendorResponse vendor = vendorService.create(VENDOR("테스트 매입처"));
//        WarehouseResponse warehouse = warehouseService.create(WAREHOUSE("테스트 창고"));
//        TireResponse tire01 = tireService.create(TIRE(brand.getId(), "PRODUCT_ID_01", 11, "패턴01"));    // size: 1656011
//        TireResponse tire02 = tireService.create(TIRE(brand.getId(), "PRODUCT_ID_02", 12, "패턴02"));    // size: 1656012
//        List<Purchase> purchases = purchaseService.create(CREATE_PURCHASE(vendor.getVendorId(),
//                CREATE_PURCHASE_CONTENT(tire01.getTireId(), "dot01", warehouse.getWarehouseId(), 1L),
//                CREATE_PURCHASE_CONTENT(tire02.getTireId(), "dot02", warehouse.getWarehouseId(), 2L)));
//        purchases.forEach(purchase -> purchaseService.confirm(purchase.getPurchaseId()));
//        CustomerResponse customer = customerService.create(CUSTOMER("test_user"));
//        clear();
//
//        log.info("매출 생성");
//        List<StockResponse> stocks = stockService.findAll();
//        SaleSimpleResponse sale = saleService.create(SALE_CREATE(customer.getCustomerId(),
//                SALE_CREATE_CONTENT(stocks.get(0).getStockId(), 1L),
//                SALE_CREATE_CONTENT(stocks.get(0).getStockId(), 2L)));
//        assertNotNull(sale.getSaleId());
//    }
//}