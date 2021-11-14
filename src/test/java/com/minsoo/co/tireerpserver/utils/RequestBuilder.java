//package com.minsoo.co.tireerpserver.utils;
//
//import com.minsoo.co.tireerpserver.purchase.model.PurchaseRequest;
//import com.minsoo.co.tireerpserver.purchase.model.content.PurchaseContentConfirmRequest;
//import com.minsoo.co.tireerpserver.purchase.model.content.PurchaseContentRequest;
//import com.minsoo.co.tireerpserver.sale.model.SaleRequest;
//import com.minsoo.co.tireerpserver.sale.model.content.SaleContentConfirmRequest;
//import com.minsoo.co.tireerpserver.sale.model.content.SaleContentRequest;
//import com.minsoo.co.tireerpserver.sale.model.content.SaleContentStockRequest;
//import com.minsoo.co.tireerpserver.shared.model.AddressDTO;
//import com.minsoo.co.tireerpserver.shared.model.BusinessInfoDTO;
//import com.minsoo.co.tireerpserver.management.model.brand.BrandRequest;
//import com.minsoo.co.tireerpserver.management.model.vendor.VendorRequest;
//import com.minsoo.co.tireerpserver.management.model.warehouse.WarehouseRequest;
//import com.minsoo.co.tireerpserver.management.model.pattern.PatternRequest;
//import com.minsoo.co.tireerpserver.stock.model.StockRequest;
//import com.minsoo.co.tireerpserver.tire.model.dot.TireDotRequest;
//import com.minsoo.co.tireerpserver.tire.model.TireRequest;
//import com.minsoo.co.tireerpserver.tire.model.memo.TireMemoRequest;
//import com.minsoo.co.tireerpserver.user.model.client.company.ClientCompanyRequest;
//
//import java.lang.reflect.Array;
//import java.time.LocalDate;
//import java.util.Arrays;
//
//public class RequestBuilder {
//
//    public static BrandRequest BRAND(String name) {
//        return BrandRequest.builder()
//                .name(name)
//                .description("테스트용 브랜드")
//                .build();
//    }
//
//    public static PatternRequest PATTERN(String name) {
//        return PatternRequest.builder()
//                .name(name)
//                .carType("SUV/RV")
//                .rank("고급형")
//                .season("여름용")
//                .quietness(false)
//                .breakingPower(false)
//                .handling(true)
//                .mileage(true)
//                .rideQuality(false)
//                .sports(true)
//                .wetSurface(false)
//                .build();
//    }
//
//    public static VendorRequest VENDOR(String name) {
//        return VendorRequest.builder()
//                .name(name)
//                .description("테스트용 매입처")
//                .businessInfo(BUSINESS_INFO())
//                .build();
//    }
//
//    public static WarehouseRequest WAREHOUSE(String name) {
//        return WarehouseRequest.builder()
//                .name(name)
//                .description("테스트용 창고")
//                .capacity(200)
//                .address(ADDRESS())
//                .build();
//    }
//
//    public static TireRequest TIRE(String tireIdentification, Long patternId, Integer inch) {
//        return TireRequest.builder()
//                .onSale(true)
//                .tireIdentification(tireIdentification)
//                .patternId(patternId)
//                .width(165)
//                .flatnessRatio(60)
//                .inch(inch)
//                .loadIndex(79)
//                .speedIndex("H")
//                .runFlat(true)
//                .sponge(false)
//                .sealing(true)
//                .oe("AO")
//                .countryOfManufacture("헝가리 외2")
//                .originalVehicle(null)
//                .note("AC")
//                .tireGroup(null)
//                .pr("DT1")
//                .lr(null)
//                .build();
//    }
//
//    public static TireDotRequest TIRE_DOT(String dot, int retailPrice) {
//        return TireDotRequest.builder()
//                .dot(dot)
//                .retailPrice(retailPrice)
//                .build();
//    }
//
//    public static TireMemoRequest TIRE_MEMO(String memo, boolean lock) {
//        return TireMemoRequest.builder()
//                .memo(memo)
//                .lock(lock)
//                .build();
//    }
//
//    public static PurchaseRequest PURCHASE(Long vendorId, LocalDate transactionDate, PurchaseContentRequest... contents) {
//        return PurchaseRequest.builder()
//                .transactionDate(transactionDate)
//                .vendorId(vendorId)
//                .contents(Arrays.asList(contents))
//                .build();
//    }
//
//    public static PurchaseContentRequest PURCHASE_CONTENT(Long tireDotId, Long quantity) {
//        return PurchaseContentRequest.builder()
//                .tireDotId(tireDotId)
//                .price(20000)
//                .quantity(quantity)
//                .build();
//    }
//
//    public static PurchaseContentConfirmRequest PURCHASE_CONTENT_CONFIRM(Long purchaseContentId, StockRequest... stockRequests) {
//        return PurchaseContentConfirmRequest.builder()
//                .purchaseContentId(purchaseContentId)
//                .stockRequests(Arrays.asList(stockRequests))
//                .build();
//    }
//
//    public static StockRequest STOCK_MODIFY(String nickname, Long warehouseId, Long quantity, Boolean lock) {
//        return StockRequest.builder()
//                .warehouseId(warehouseId)
//                .nickname(nickname)
//                .quantity(quantity)
//                .lock(lock)
//                .build();
//    }
//
//    public static SaleRequest SALE(Long clientCompanyId, LocalDate transactionDate, LocalDate confirmedDate, SaleContentRequest... contents) {
//        return SaleRequest.builder()
//                .clientCompanyId(clientCompanyId)
//                .transactionDate(transactionDate)
//                .confirmedDate(confirmedDate)
//                .desiredDeliveryDate(null)
//                .contents(Arrays.asList(contents))
//                .build();
//    }
//
//    public static SaleContentRequest SALE_CONTENT(Long tireDotId, Long quantity) {
//        return SaleContentRequest.builder()
//                .price(20000)
//                .quantity(quantity)
//                .tireDotId(tireDotId)
//                .build();
//    }
//
//    public static SaleContentConfirmRequest SALE_CONTENT_CONFIRM(Long saleContentId, SaleContentStockRequest... stockRequests) {
//        return SaleContentConfirmRequest.builder()
//                .saleContentId(saleContentId)
//                .stockRequests(Arrays.asList(stockRequests))
//                .build();
//    }
//
//    public static SaleContentStockRequest SALE_STOCK(Long stockId, Long quantity) {
//        return SaleContentStockRequest.builder()
//                .stockId(stockId)
//                .quantity(quantity)
//                .build();
//    }
//
//    public static ClientCompanyRequest CLIENT_COMPANY(String name) {
//        return ClientCompanyRequest.builder()
//                .name(name)
//                .description("description")
//                .businessInfo(BUSINESS_INFO())
//                .build();
//    }
//
//    private static AddressDTO ADDRESS() {
//        return AddressDTO.builder()
//                .city("서울시")
//                .streetAddress("서울시 강동구 천호대로 17길 17")
//                .detailAddress("123동 456호")
//                .zipCode(123456)
//                .build();
//    }
//
//    private static BusinessInfoDTO BUSINESS_INFO() {
//        return BusinessInfoDTO.builder()
//                .businessNumber("123456")
//                .businessName("테스트 사업자")
//                .businessType("테스트 사업")
//                .address(ADDRESS())
//                .fax("02-123-4567")
//                .email("test@test.com")
//                .representative("대표자")
//                .managerPhoneNumber("010-1234-5678")
//                .manager("담당자")
//                .managerPhoneNumber("010-1234-5678")
//                .build();
//    }
//}
