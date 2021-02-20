package com.minsoo.co.tireerpserver.utils;

import com.minsoo.co.tireerpserver.model.code.TireOption;
import com.minsoo.co.tireerpserver.model.dto.general.AddressDTO;
import com.minsoo.co.tireerpserver.model.dto.general.BusinessInfoDTO;
import com.minsoo.co.tireerpserver.model.dto.management.brand.BrandRequest;
import com.minsoo.co.tireerpserver.model.dto.management.vendor.VendorRequest;
import com.minsoo.co.tireerpserver.model.dto.management.warehouse.WarehouseRequest;
import com.minsoo.co.tireerpserver.model.dto.purchase.PurchaseCreateRequest;
import com.minsoo.co.tireerpserver.model.dto.purchase.PurchaseCreateRequestContent;
import com.minsoo.co.tireerpserver.model.dto.purchase.PurchaseUpdateRequest;
import com.minsoo.co.tireerpserver.model.dto.tire.TireRequest;

import java.time.LocalDate;
import java.util.Arrays;

public class RequestBuilder {

    public static BrandRequest BRAND(String name) {
        return BrandRequest.builder()
                .name(name)
                .description("테스트용 브랜드")
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

    public static TireRequest TIRE(Long brandId, String productId, String label) {
        return TireRequest.builder()
                .brandId(brandId)
                .label(label)
                .width(165)
                .flatnessRatio(60)
                .inch(14)
                .loadIndex(79)
                .speedIndex("H")
                .season("겨울")
                .price(120000)
                .runFlat(true)
                .option(TireOption.SEAL)
                .oe("AO")
                .pattern("PZero")
                .productId(productId)
                .build();
    }

    public static PurchaseCreateRequestContent CREATE_PURCHASE_CONTENT(Long tireId, String dot, Long warehouseId, Long quantity) {
        return PurchaseCreateRequestContent.builder()
                .tireId(tireId)
                .dot(dot)
                .warehouseId(warehouseId)
                .price(20000)
                .quantity(quantity)
                .build();
    }

    public static PurchaseCreateRequest CREATE_PURCHASE(Long vendorId, PurchaseCreateRequestContent... contents) {
        return PurchaseCreateRequest.builder()
                .purchasedDate(LocalDate.now())
                .vendorId(vendorId)
                .contents(Arrays.asList(contents))
                .build();
    }

    public static PurchaseUpdateRequest UPDATE_PURCHASE(Long vendorId, Long tireId, String dot, Long warehouseId, Long quantity) {
        return PurchaseUpdateRequest.builder()
                .vendorId(vendorId)
                .purchasedDate(LocalDate.now())
                .tireId(tireId)
                .dot(dot)
                .warehouseId(warehouseId)
                .price(20000)
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
