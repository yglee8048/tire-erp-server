package com.minsoo.co.tireerpserver.utils;

import com.minsoo.co.tireerpserver.model.dto.account.account.AccountRequest;
import com.minsoo.co.tireerpserver.model.dto.account.customer.CustomerRequest;
import com.minsoo.co.tireerpserver.model.dto.general.AddressDTO;
import com.minsoo.co.tireerpserver.model.dto.general.BusinessInfoDTO;
import com.minsoo.co.tireerpserver.model.dto.management.brand.BrandRequest;
import com.minsoo.co.tireerpserver.model.dto.management.vendor.VendorRequest;
import com.minsoo.co.tireerpserver.model.dto.management.warehouse.WarehouseRequest;
import com.minsoo.co.tireerpserver.model.dto.purchase.*;
import com.minsoo.co.tireerpserver.model.dto.purchase.content.PurchaseContentConfirmRequest;
import com.minsoo.co.tireerpserver.model.dto.purchase.content.PurchaseContentRequest;
import com.minsoo.co.tireerpserver.model.dto.sale.SaleCreateRequest;
import com.minsoo.co.tireerpserver.model.dto.sale.SaleCreateRequestContent;
import com.minsoo.co.tireerpserver.model.dto.management.pattern.PatternRequest;
import com.minsoo.co.tireerpserver.model.dto.stock.StockRequest;
import com.minsoo.co.tireerpserver.model.dto.tire.dot.TireDotRequest;
import com.minsoo.co.tireerpserver.model.dto.tire.TireRequest;

import java.time.LocalDate;
import java.util.Arrays;

public class RequestBuilder {

    public static AccountRequest ACOCUNT(String userId, String userPw) {
        return AccountRequest.builder()
                .userId(userId)
                .userPw(userPw)
                .build();
    }

    public static BrandRequest BRAND(String name) {
        return BrandRequest.builder()
                .name(name)
                .description("테스트용 브랜드")
                .build();
    }

    public static PatternRequest PATTERN(String name) {
        return PatternRequest.builder()
                .name(name)
                .carType("SUV/RV")
                .rank("고급형")
                .season("여름용")
                .quietness(false)
                .breakingPower(false)
                .handling(true)
                .mileage(true)
                .rideQuality(false)
                .sports(true)
                .wetSurface(false)
                .build();
    }

    public static VendorRequest VENDOR(String name) {
        return VendorRequest.builder()
                .name(name)
                .description("테스트용 매입처")
                .businessInfo(BUSINESS_INFO())
                .build();
    }

    public static WarehouseRequest WAREHOUSE(String name) {
        return WarehouseRequest.builder()
                .name(name)
                .description("테스트용 창고")
                .capacity(200)
                .address(ADDRESS())
                .build();
    }

    public static TireRequest TIRE(String tireIdentification, Long patternId, Integer inch) {
        return TireRequest.builder()
                .onSale(true)
                .tireIdentification(tireIdentification)
                .patternId(patternId)
                .width(165)
                .flatnessRatio(60)
                .inch(inch)
                .loadIndex(79)
                .speedIndex("H")
                .runFlat(true)
                .sponge(false)
                .sealing(true)
                .oe("AO")
                .countryOfManufacture("헝가리 외2")
                .originalVehicle(null)
                .note("AC")
                .group(null)
                .pr("DT1")
                .lr(null)
                .build();
    }

    public static TireDotRequest TIRE_DOT(String dot, Long retailPrice) {
        return TireDotRequest.builder()
                .dot(dot)
                .retailPrice(retailPrice)
                .build();
    }

    public static PurchaseRequest PURCHASE(Long vendorId, LocalDate purchaseDate, PurchaseContentRequest... contents) {
        return PurchaseRequest.builder()
                .purchaseDate(purchaseDate)
                .vendorId(vendorId)
                .contents(Arrays.asList(contents))
                .build();
    }

    public static PurchaseContentRequest PURCHASE_CONTENT(Long tireDotId, Long quantity) {
        return PurchaseContentRequest.builder()
                .tireDotId(tireDotId)
                .price(20000)
                .quantity(quantity)
                .build();
    }

    public static PurchaseContentConfirmRequest PURCHASE_CONFIRM(Long purchaseContentId, StockRequest... stockRequests) {
        return PurchaseContentConfirmRequest.builder()
                .purchaseContentId(purchaseContentId)
                .stockRequests(Arrays.asList(stockRequests))
                .build();
    }

    public static StockRequest STOCK_MODIFY(String nickname, Long warehouseId, Long quantity, Boolean lock) {
        return StockRequest.builder()
                .warehouseId(warehouseId)
                .nickname(nickname)
                .quantity(quantity)
                .lock(lock)
                .build();
    }

    public static CustomerRequest CUSTOMER(String userId) {
        return CustomerRequest.builder()
                .name("거래처 이름")
                .description("설명")
                .userId(userId)
                .userPw("password")
                .businessInfo(BUSINESS_INFO())
                .build();
    }

    public static SaleCreateRequest SALE_CREATE(Long customerId, SaleCreateRequestContent... contents) {
        return SaleCreateRequest.builder()
                .saleDate(LocalDate.now())
                .customerId(customerId)
                .contents(Arrays.asList(contents))
                .build();
    }

    public static SaleCreateRequestContent SALE_CREATE_CONTENT(Long stockId, Long quantity) {
        return SaleCreateRequestContent.builder()
                .stockId(stockId)
                .price(240000)
                .quantity(quantity)
                .build();
    }

    private static AddressDTO ADDRESS() {
        return AddressDTO.builder()
                .city("서울시")
                .streetAddress("서울시 강동구 천호대로 17길 17")
                .detailAddress("123동 456호")
                .zipCode(123456)
                .build();
    }

    private static BusinessInfoDTO BUSINESS_INFO() {
        return BusinessInfoDTO.builder()
                .businessNumber("123456")
                .businessName("테스트 사업자")
                .businessType("테스트 사업")
                .address(ADDRESS())
                .fax("02-123-4567")
                .emailAddress("test@test.com")
                .representative("대표자")
                .managerPhoneNumber("010-1234-5678")
                .manager("담당자")
                .managerPhoneNumber("010-1234-5678")
                .build();
    }
}
