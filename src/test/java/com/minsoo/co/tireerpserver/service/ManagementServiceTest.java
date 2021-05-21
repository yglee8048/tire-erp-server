//package com.minsoo.co.tireerpserver.service;
//
//import com.minsoo.co.tireerpserver.api.error.exceptions.AlreadyExistException;
//import com.minsoo.co.tireerpserver.model.dto.management.brand.BrandRequest;
//import com.minsoo.co.tireerpserver.model.dto.management.vendor.VendorRequest;
//import com.minsoo.co.tireerpserver.model.dto.management.vendor.VendorResponse;
//import com.minsoo.co.tireerpserver.model.dto.management.warehouse.WarehouseRequest;
//import com.minsoo.co.tireerpserver.model.dto.management.warehouse.WarehouseResponse;
//import com.minsoo.co.tireerpserver.model.entity.entities.management.Brand;
//import com.minsoo.co.tireerpserver.service.management.BrandService;
//import com.minsoo.co.tireerpserver.service.management.VendorService;
//import com.minsoo.co.tireerpserver.service.management.WarehouseService;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import static com.minsoo.co.tireerpserver.utils.RequestBuilder.*;
//import static org.assertj.core.api.Assertions.*;
//
//public class ManagementServiceTest extends ServiceTest {
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
//    /**
//     * 1. 브랜드의 이름은 중복될 수 없다.
//     */
//    @Test
//    @DisplayName("브랜드 생성 테스트")
//    void brandCreateTest() {
//        log.debug("브랜드 생성 테스트");
//        BrandRequest brandRequest = BRAND("브랜드 테스트");
//        Brand brand = brandService.create(brandRequest);
//        assertThat(brand.getName()).isEqualTo("브랜드 테스트");
//
//        log.debug("이름 중복 생성 시 예외 발생");
//        BrandRequest duplicateRequest = BRAND("브랜드 테스트");
//        assertThatThrownBy(() -> brandService.create(duplicateRequest))
//                .isInstanceOf(AlreadyExistException.class);
//    }
//
//    /**
//     * 매입처의 이름은 중복될 수 없다.
//     */
//    @Test
//    @DisplayName("매입처 생성 테스트")
//    void vendorCreateTest() {
//        log.debug("매입처 생성 테스트");
//        VendorRequest vendorRequest = VENDOR("매입처 테스트");
//        VendorResponse vendorResponse = vendorService.create(vendorRequest);
//        assertThat(vendorResponse.getName()).isEqualTo("매입처 테스트");
//
//        log.debug("이름 중복 생성 시 예외 발생");
//        VendorRequest duplicateRequest = VENDOR("매입처 테스트");
//        assertThatThrownBy(() -> vendorService.create(duplicateRequest))
//                .isInstanceOf(AlreadyExistException.class);
//    }
//
//    /**
//     * 창고의 이름은 중복될 수 없다.
//     */
//    @Test
//    @DisplayName("창고 생성 테스트")
//    void warehouseCreateTest() {
//        log.debug("창고 생성 테스트");
//        WarehouseRequest warehouseRequest = WAREHOUSE("창고 테스트");
//        WarehouseResponse warehouseResponse = warehouseService.create(warehouseRequest);
//        assertThat(warehouseResponse.getName()).isEqualTo("창고 테스트");
//
//        log.debug("이름 중복 생성 시 예외 발생");
//        WarehouseRequest duplicateRequest = WAREHOUSE("창고 테스트");
//        assertThatThrownBy(() -> warehouseService.create(duplicateRequest))
//                .isInstanceOf(AlreadyExistException.class);
//    }
//}
