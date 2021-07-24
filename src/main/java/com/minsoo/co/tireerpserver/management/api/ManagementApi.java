package com.minsoo.co.tireerpserver.management.api;

import com.minsoo.co.tireerpserver.management.model.pattern.PatternRequest;
import com.minsoo.co.tireerpserver.management.model.pattern.PatternResponse;
import com.minsoo.co.tireerpserver.management.entity.Brand;
import com.minsoo.co.tireerpserver.management.entity.Pattern;
import com.minsoo.co.tireerpserver.management.entity.Vendor;
import com.minsoo.co.tireerpserver.management.entity.Warehouse;
import com.minsoo.co.tireerpserver.shared.model.ApiResponse;
import com.minsoo.co.tireerpserver.management.model.brand.BrandRequest;
import com.minsoo.co.tireerpserver.management.model.brand.BrandResponse;
import com.minsoo.co.tireerpserver.management.model.vendor.VendorRequest;
import com.minsoo.co.tireerpserver.management.model.vendor.VendorResponse;
import com.minsoo.co.tireerpserver.management.model.warehouse.WarehouseRequest;
import com.minsoo.co.tireerpserver.management.model.warehouse.WarehouseResponse;
import com.minsoo.co.tireerpserver.management.service.BrandService;
import com.minsoo.co.tireerpserver.management.service.VendorService;
import com.minsoo.co.tireerpserver.management.service.WarehouseService;
import com.minsoo.co.tireerpserver.management.service.PatternService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1")
@RequiredArgsConstructor
public class ManagementApi {

    private final BrandService brandService;
    private final PatternService patternService;
    private final VendorService vendorService;
    private final WarehouseService warehouseService;

    // BRAND
    @GetMapping(value = "/brands")
    @Operation(summary = "제조사 목록 조회")
    public ApiResponse<List<BrandResponse>> findAllBrands() {
        return ApiResponse.OK(brandService.findAll()
                .stream()
                .map(BrandResponse::of)
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/brands/{brandId}")
    @Operation(summary = "제조사 상세 조회")
    public ApiResponse<BrandResponse> findBrandById(@PathVariable Long brandId) {
        return ApiResponse.OK(BrandResponse.of(brandService.findById(brandId)));
    }

    @PostMapping(value = "/brands")
    @Operation(summary = "제조사 생성")
    public ResponseEntity<ApiResponse<Void>> createBrand(@RequestBody @Valid BrandRequest brandRequest) {
        Brand brand = brandService.create(brandRequest);
        return ApiResponse.CREATED(
                linkTo(methodOn(ManagementApi.class).findBrandById(brand.getId())).toUri());
    }

    @PutMapping(value = "/brands/{brandId}")
    @Operation(summary = "제조사 수정")
    public ApiResponse<Void> updateBrand(@PathVariable Long brandId,
                                           @RequestBody @Valid BrandRequest brandUpdateRequest) {
        brandService.update(brandId, brandUpdateRequest);
        return ApiResponse.NO_CONTENT;
    }

    @DeleteMapping(value = "/brands/{brandId}")
    @Operation(summary = "제조사 삭제")
    public ApiResponse<Void> deleteBrand(@PathVariable Long brandId) {
        brandService.removeById(brandId);
        return ApiResponse.NO_CONTENT;
    }

    // PATTERN
    @GetMapping("/brands/{brandId}/patterns")
    @Operation(summary = "패턴 목록 조회")
    public ApiResponse<List<PatternResponse>> findAllPatternsByBrandId(@PathVariable(name = "brandId") Long brandId) {
        return ApiResponse.OK(patternService.findAllByBrandId(brandId)
                .stream()
                .map(PatternResponse::of)
                .collect(Collectors.toList()));
    }

    @GetMapping("/brands/{brandId}/patterns/{patternId}")
    @Operation(summary = "패턴 상세 조회")
    public ApiResponse<PatternResponse> findPatternByIds(@PathVariable(name = "brandId") Long brandId,
                                                         @PathVariable(name = "patternId") Long patternId) {
        return ApiResponse.OK(PatternResponse.of(patternService.findById(brandId, patternId)));
    }

    @PostMapping("/brands/{brandId}/patterns")
    @Operation(summary = "패턴 생성")
    public ResponseEntity<ApiResponse<Void>> createPattern(@PathVariable(name = "brandId") Long brandId,
                                                             @RequestBody @Valid PatternRequest patternRequest) {
        Pattern pattern = patternService.create(brandId, patternRequest);
        return ApiResponse.CREATED(
                linkTo(methodOn(ManagementApi.class).findPatternByIds(brandId, pattern.getId())).toUri());
    }

    @PutMapping("/brands/{brandId}/patterns/{patternId}")
    @Operation(summary = "패턴 수정")
    public ApiResponse<Void> updatePattern(@PathVariable(name = "brandId") Long brandId,
                                             @PathVariable(name = "patternId") Long patternId,
                                             @RequestBody @Valid PatternRequest patternRequest) {
        patternService.update(brandId, patternId, patternRequest);
        return ApiResponse.NO_CONTENT;
    }

    @DeleteMapping("/brands/{brandId}/patterns/{patternId}")
    @Operation(summary = "패턴 삭제")
    public ApiResponse<Void> deletePatternById(@PathVariable(name = "brandId") Long brandId,
                                                 @PathVariable(name = "patternId") Long patternId) {
        patternService.removeById(brandId, patternId);
        return ApiResponse.NO_CONTENT;
    }

    // VENDOR
    @GetMapping(value = "/vendors")
    @Operation(summary = "매입처 목록 조회")
    public ApiResponse<List<VendorResponse>> findAllVendors() {
        return ApiResponse.OK(vendorService.findAll()
                .stream()
                .map(VendorResponse::of)
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/vendors/{vendorId}")
    @Operation(summary = "매입처 상세 조회")
    public ApiResponse<VendorResponse> findVendorById(@PathVariable Long vendorId) {
        return ApiResponse.OK(VendorResponse.of(vendorService.findById(vendorId)));
    }

    @PostMapping(value = "/vendors")
    @Operation(summary = "매입처 생성")
    public ResponseEntity<ApiResponse<Void>> createVendor(@RequestBody @Valid VendorRequest vendorRequest) {
        Vendor vendor = vendorService.create(vendorRequest);
        return ApiResponse.CREATED(
                linkTo(methodOn(ManagementApi.class).findVendorById(vendor.getId())).toUri());
    }

    @PutMapping(value = "/vendors/{vendorId}")
    @Operation(summary = "매입처 수정")
    public ApiResponse<Void> updateVendor(@PathVariable Long vendorId,
                                            @RequestBody @Valid VendorRequest vendorUpdateRequest) {
        vendorService.update(vendorId, vendorUpdateRequest);
        return ApiResponse.NO_CONTENT;
    }

    @DeleteMapping(value = "/vendors/{vendorId}")
    @Operation(summary = "매입처 삭제")
    public ApiResponse<Void> deleteVendor(@PathVariable Long vendorId) {
        vendorService.removeById(vendorId);
        return ApiResponse.NO_CONTENT;
    }

    // WAREHOUSE
    @GetMapping(value = "/warehouses")
    @Operation(summary = "창고 목록 조회")
    public ApiResponse<List<WarehouseResponse>> findAllWarehouses() {
        return ApiResponse.OK(warehouseService.findAll()
                .stream()
                .map(WarehouseResponse::of)
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/warehouses/{warehouseId}")
    @Operation(summary = "창고 상세 조회")
    public ApiResponse<WarehouseResponse> findWarehouseById(@PathVariable Long warehouseId) {
        return ApiResponse.OK(WarehouseResponse.of(warehouseService.findById(warehouseId)));
    }

    @PostMapping(value = "/warehouses")
    @Operation(summary = "창고 생성")
    public ResponseEntity<ApiResponse<Void>> createWarehouse(@RequestBody @Valid WarehouseRequest warehouseRequest) {
        Warehouse warehouse = warehouseService.create(warehouseRequest);
        return ApiResponse.CREATED(
                linkTo(methodOn(ManagementApi.class).findWarehouseById(warehouse.getId())).toUri());
    }

    @PutMapping(value = "/warehouses/{warehouseId}")
    @Operation(summary = "창고 수정")
    public ApiResponse<Void> updateWarehouse(@PathVariable Long warehouseId,
                                               @RequestBody @Valid WarehouseRequest warehouseUpdateRequest) {
        warehouseService.update(warehouseId, warehouseUpdateRequest);
        return ApiResponse.NO_CONTENT;
    }

    @DeleteMapping(value = "/warehouses/{warehouseId}")
    @Operation(summary = "창고 삭제")
    public ApiResponse<Void> deleteWarehouse(@PathVariable Long warehouseId) {
        warehouseService.removeById(warehouseId);
        return ApiResponse.NO_CONTENT;
    }
}
