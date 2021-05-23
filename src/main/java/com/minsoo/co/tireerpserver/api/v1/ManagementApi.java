package com.minsoo.co.tireerpserver.api.v1;

import com.minsoo.co.tireerpserver.model.dto.management.pattern.PatternRequest;
import com.minsoo.co.tireerpserver.model.dto.management.pattern.PatternResponse;
import com.minsoo.co.tireerpserver.model.entity.entities.management.Pattern;
import com.minsoo.co.tireerpserver.model.response.ApiResponse;
import com.minsoo.co.tireerpserver.model.dto.management.brand.BrandRequest;
import com.minsoo.co.tireerpserver.model.dto.management.brand.BrandResponse;
import com.minsoo.co.tireerpserver.model.dto.management.vendor.VendorRequest;
import com.minsoo.co.tireerpserver.model.dto.management.vendor.VendorResponse;
import com.minsoo.co.tireerpserver.model.dto.management.warehouse.WarehouseRequest;
import com.minsoo.co.tireerpserver.model.dto.management.warehouse.WarehouseResponse;
import com.minsoo.co.tireerpserver.service.management.BrandService;
import com.minsoo.co.tireerpserver.service.management.VendorService;
import com.minsoo.co.tireerpserver.service.management.WarehouseService;
import com.minsoo.co.tireerpserver.service.management.PatternService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
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
    public ApiResponse<List<BrandResponse>> findAllBrands() {
        return ApiResponse.OK(brandService.findAll()
                .stream()
                .map(BrandResponse::of)
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/brands/{brandId}")
    public ApiResponse<BrandResponse> findBrandById(@PathVariable Long brandId) {
        return ApiResponse.OK(BrandResponse.of(brandService.findById(brandId)));
    }

    @PostMapping(value = "/brands")
    public ResponseEntity<ApiResponse<Object>> createBrand(@RequestBody @Valid BrandRequest brandRequest) {
        return ApiResponse.CREATED(
                linkTo(methodOn(ManagementApi.class).findBrandById(brandService.create(brandRequest).getId())).toUri());
    }

    @PutMapping(value = "/brands/{brandId}")
    public ApiResponse<Object> updateBrand(@PathVariable Long brandId,
                                           @RequestBody @Valid BrandRequest brandUpdateRequest) {
        brandService.update(brandId, brandUpdateRequest);
        return ApiResponse.OK;
    }

    @DeleteMapping(value = "/brands/{brandId}")
    public ApiResponse<Object> deleteBrand(@PathVariable Long brandId) {
        brandService.removeById(brandId);
        return ApiResponse.OK;
    }

    // PATTERN
    @GetMapping("/brands/{brandId}/patterns")
    public ApiResponse<List<PatternResponse>> findAllPatternsByBrandId(@PathVariable(name = "brandId") Long brandId) {
        return ApiResponse.OK(patternService.findAllByBrandId(brandId)
                .stream()
                .map(PatternResponse::of)
                .collect(Collectors.toList()));
    }

    @GetMapping("/brands/{brandId}/patterns/{patternId}")
    public ApiResponse<PatternResponse> findPatternByIds(@PathVariable(name = "brandId") Long brandId,
                                                         @PathVariable(name = "patternId") Long patternId) {
        return ApiResponse.OK(PatternResponse.of(patternService.findByIds(brandId, patternId)));
    }

    @PostMapping("/brands/{brandId}/patterns")
    public ResponseEntity<ApiResponse<Object>> createPattern(@PathVariable(name = "brandId") Long brandId,
                                                             @RequestBody @Valid PatternRequest patternRequest) {
        return ApiResponse.CREATED(
                linkTo(methodOn(ManagementApi.class).findPatternByIds(brandId, patternService.create(brandId, patternRequest).getId())).toUri());
    }

    @PutMapping("/brands/{brandId}/patterns/{patternId}")
    public ApiResponse<Object> updatePattern(@PathVariable(name = "brandId") Long brandId,
                                             @PathVariable(name = "patternId") Long patternId,
                                             @RequestBody @Valid PatternRequest patternRequest) {
        patternService.update(brandId, patternId, patternRequest);
        return ApiResponse.OK;
    }

    @DeleteMapping("/brands/{brandId}/patterns/{patternId}")
    public ApiResponse<Object> deletePatternById(@PathVariable(name = "brandId") Long brandId,
                                                 @PathVariable(name = "patternId") Long patternId) {
        patternService.removeByIds(brandId, patternId);
        return ApiResponse.OK;
    }

    // VENDOR
    @GetMapping(value = "/vendors")
    public ApiResponse<List<VendorResponse>> findAllVendors() {
        return ApiResponse.OK(vendorService.findAll()
                .stream()
                .map(VendorResponse::of)
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/vendors/{vendorId}")
    public ApiResponse<VendorResponse> findVendorById(@PathVariable Long vendorId) {
        return ApiResponse.OK(VendorResponse.of(vendorService.findById(vendorId)));
    }

    @PostMapping(value = "/vendors")
    public ResponseEntity<ApiResponse<Object>> createVendor(@RequestBody @Valid VendorRequest vendorRequest) {
        return ApiResponse.CREATED(
                linkTo(methodOn(ManagementApi.class).findVendorById(vendorService.create(vendorRequest).getId())).toUri());
    }

    @PutMapping(value = "/vendors/{vendorId}")
    public ApiResponse<Object> updateVendor(@PathVariable Long vendorId,
                                            @RequestBody @Valid VendorRequest vendorUpdateRequest) {
        vendorService.update(vendorId, vendorUpdateRequest);
        return ApiResponse.OK;
    }

    @DeleteMapping(value = "/vendors/{vendorId}")
    public ApiResponse<Object> deleteVendor(@PathVariable Long vendorId) {
        vendorService.removeById(vendorId);
        return ApiResponse.OK;
    }

    // WAREHOUSE
    @GetMapping(value = "/warehouses")
    public ApiResponse<List<WarehouseResponse>> findAllWarehouses() {
        return ApiResponse.OK(warehouseService.findAll()
                .stream()
                .map(WarehouseResponse::of)
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/warehouses/{warehouseId}")
    public ApiResponse<WarehouseResponse> findWarehouseById(@PathVariable Long warehouseId) {
        return ApiResponse.OK(WarehouseResponse.of(warehouseService.findById(warehouseId)));
    }

    @PostMapping(value = "/warehouses")
    public ResponseEntity<ApiResponse<Object>> createWarehouse(@RequestBody @Valid WarehouseRequest warehouseRequest) {
        return ApiResponse.CREATED(
                linkTo(methodOn(ManagementApi.class).findWarehouseById(warehouseService.create(warehouseRequest).getId())).toUri());
    }

    @PutMapping(value = "/warehouses/{warehouseId}")
    public ApiResponse<Object> updateWarehouse(@PathVariable Long warehouseId,
                                               @RequestBody @Valid WarehouseRequest warehouseUpdateRequest) {
        warehouseService.update(warehouseId, warehouseUpdateRequest);
        return ApiResponse.OK;
    }

    @DeleteMapping(value = "/warehouses/{warehouseId}")
    public ApiResponse<Object> deleteWarehouse(@PathVariable Long warehouseId) {
        warehouseService.removeById(warehouseId);
        return ApiResponse.OK;
    }
}
