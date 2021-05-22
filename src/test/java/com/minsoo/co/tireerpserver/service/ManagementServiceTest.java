package com.minsoo.co.tireerpserver.service;

import com.minsoo.co.tireerpserver.api.error.exceptions.AlreadyExistException;
import com.minsoo.co.tireerpserver.model.code.PatternOption;
import com.minsoo.co.tireerpserver.model.dto.management.brand.BrandRequest;
import com.minsoo.co.tireerpserver.model.dto.management.vendor.VendorRequest;
import com.minsoo.co.tireerpserver.model.dto.management.warehouse.WarehouseRequest;
import com.minsoo.co.tireerpserver.model.dto.management.pattern.PatternRequest;
import com.minsoo.co.tireerpserver.model.entity.entities.management.Brand;
import com.minsoo.co.tireerpserver.model.entity.entities.management.Vendor;
import com.minsoo.co.tireerpserver.model.entity.entities.management.Warehouse;
import com.minsoo.co.tireerpserver.model.entity.entities.management.Pattern;
import com.minsoo.co.tireerpserver.model.entity.entities.management.PatternOptions;
import com.minsoo.co.tireerpserver.service.management.BrandService;
import com.minsoo.co.tireerpserver.service.management.VendorService;
import com.minsoo.co.tireerpserver.service.management.WarehouseService;
import com.minsoo.co.tireerpserver.service.management.PatternService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.minsoo.co.tireerpserver.utils.RequestBuilder.*;
import static org.assertj.core.api.Assertions.*;

public class ManagementServiceTest extends ServiceTest {

    @Autowired
    BrandService brandService;

    @Autowired
    PatternService patternService;

    @Autowired
    VendorService vendorService;

    @Autowired
    WarehouseService warehouseService;

    /**
     * 1. 브랜드의 이름은 중복될 수 없다.
     */
    @Test
    @DisplayName("브랜드 생성 테스트")
    void brandCreateTest() {
        log.debug("브랜드 생성 테스트");
        BrandRequest brandRequest = BRAND("브랜드 테스트");
        Brand brand = brandService.create(brandRequest);
        assertThat(brand.getName()).isEqualTo("브랜드 테스트");

        log.debug("이름 중복 생성 시 예외 발생");
        BrandRequest duplicateRequest = BRAND("브랜드 테스트");
        assertThatThrownBy(() -> brandService.create(duplicateRequest))
                .isInstanceOf(AlreadyExistException.class);
    }

    @Test
    @DisplayName("패턴 생성 테스트")
    void patternCreateTest() {
        log.debug("패턴 생성 테스트");
        // 브랜드 생성
        BrandRequest brandRequest = BRAND("브랜드 테스트");
        Brand brand = brandService.create(brandRequest);

        // 패턴 생성
        List<PatternOption> patternOptions = new ArrayList<>();
        patternOptions.add(PatternOption.HANDLING);
        patternOptions.add(PatternOption.MILEAGE);
        PatternRequest patternRequest = PATTERN("패턴 이름", patternOptions);
        Pattern pattern = patternService.create(brand.getId(), patternRequest);
        assertThat(pattern.getName()).isEqualTo("패턴 이름");

        List<PatternOption> options = pattern.getOptions().stream()
                .map(PatternOptions::getOption)
                .collect(Collectors.toList());
        assertThat(options.contains(PatternOption.HANDLING)).isTrue();
        assertThat(options.contains(PatternOption.MILEAGE)).isTrue();

        log.debug("이름 중복 생성 시 예외 발생");
        PatternRequest duplicateRequest = PATTERN("패턴 이름", patternOptions);
        assertThatThrownBy(() -> patternService.create(brand.getId(), duplicateRequest))
                .isInstanceOf(AlreadyExistException.class);
    }

    /**
     * 매입처의 이름은 중복될 수 없다.
     */
    @Test
    @DisplayName("매입처 생성 테스트")
    void vendorCreateTest() {
        log.debug("매입처 생성 테스트");
        VendorRequest vendorRequest = VENDOR("매입처 테스트");
        Vendor vendor = vendorService.create(vendorRequest);
        assertThat(vendor.getName()).isEqualTo("매입처 테스트");

        log.debug("이름 중복 생성 시 예외 발생");
        VendorRequest duplicateRequest = VENDOR("매입처 테스트");
        assertThatThrownBy(() -> vendorService.create(duplicateRequest))
                .isInstanceOf(AlreadyExistException.class);
    }

    /**
     * 창고의 이름은 중복될 수 없다.
     */
    @Test
    @DisplayName("창고 생성 테스트")
    void warehouseCreateTest() {
        log.debug("창고 생성 테스트");
        WarehouseRequest warehouseRequest = WAREHOUSE("창고 테스트");
        Warehouse warehouse = warehouseService.create(warehouseRequest);
        assertThat(warehouse.getName()).isEqualTo("창고 테스트");

        log.debug("이름 중복 생성 시 예외 발생");
        WarehouseRequest duplicateRequest = WAREHOUSE("창고 테스트");
        assertThatThrownBy(() -> warehouseService.create(duplicateRequest))
                .isInstanceOf(AlreadyExistException.class);
    }
}
