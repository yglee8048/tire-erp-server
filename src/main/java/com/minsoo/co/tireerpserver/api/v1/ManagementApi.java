package com.minsoo.co.tireerpserver.api.v1;

import com.minsoo.co.tireerpserver.model.dto.management.brand.BrandSimpleResponse;
import com.minsoo.co.tireerpserver.model.dto.management.vendor.VendorSimpleResponse;
import com.minsoo.co.tireerpserver.model.dto.management.warehouse.WarehouseSimpleResponse;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
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
    private final VendorService vendorService;
    private final WarehouseService warehouseService;

    // BRAND
    @GetMapping(value = "/brands")
    @Operation(summary = "브랜드 목록 조회", description = "브랜드 목록을 조회한다.")
    public ApiResponse<List<BrandResponse>> findAllBrands() {
        return ApiResponse.OK(brandService.findAll()
                .stream()
                .map(BrandResponse::of)
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/brands/{brandId}")
    @Operation(summary = "브랜드 상세 조회", description = "브랜드의 상세 정보를 조회한다.")
    @Parameters({@Parameter(name = "brandId", description = "브랜드 ID", example = "201324", required = true)})
    public ApiResponse<BrandResponse> findBrandById(@PathVariable Long brandId) {
        return ApiResponse.OK(BrandResponse.of(brandService.findById(brandId)));
    }

    @GetMapping(value = "/brands/names")
    @Operation(summary = "브랜드 이름 목록 조회", description = "브랜드 이름의 목록을 조회한다.")
    public ApiResponse<List<BrandSimpleResponse>> findAllBrandNames() {
        return ApiResponse.OK(brandService.findAllBrandNames());
    }

    @PostMapping(value = "/brands")
    @Operation(summary = "브랜드 생성", description = "브랜드를 생성한다.")
    public ResponseEntity<ApiResponse<String>> createBrand(@RequestBody @Valid BrandRequest brandRequest) {
        return ApiResponse.CREATED(
                linkTo(methodOn(ManagementApi.class, findBrandById(brandService.create(brandRequest).getId()))).toUri());
    }

    @PutMapping(value = "/brands/{brandId}")
    @Operation(summary = "브랜드 수정", description = "브랜드를 수정한다.")
    @Parameters({@Parameter(name = "brandId", description = "브랜드 ID", example = "201324", required = true)})
    public ApiResponse<String> updateBrand(@PathVariable Long brandId,
                                           @RequestBody @Valid BrandRequest brandUpdateRequest) {
        brandService.update(brandId, brandUpdateRequest);
        return ApiResponse.OK;
    }

    @DeleteMapping(value = "/brands/{brandId}")
    @Operation(summary = "브랜드 삭제", description = "브랜드를 삭제한다.")
    @Parameters({@Parameter(name = "brandId", description = "브랜드 ID", example = "201324", required = true)})
    public ApiResponse<String> deleteBrand(@PathVariable Long brandId) {
        brandService.removeById(brandId);
        return ApiResponse.OK;
    }

    // VENDOR
    @GetMapping(value = "/vendors")
    @Operation(summary = "매입처 목록 조회", description = "매입처 목록을 조회한다.")
    public ApiResponse<List<VendorResponse>> findAllVendors() {
        return ApiResponse.OK(vendorService.findAll()
                .stream()
                .map(VendorResponse::of)
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/vendors/names")
    @Operation(summary = "매입처 이름 목록 조회", description = "매입처 이름의 목록을 조회한다.")
    public ApiResponse<List<VendorSimpleResponse>> findAllVendorNames() {
        return ApiResponse.OK(vendorService.findAllVendorNames());
    }

    @GetMapping(value = "/vendors/{vendorId}")
    @Operation(summary = "매입처 상세 조회", description = "매입처의 상세 정보를 조회한다.")
    @Parameters({@Parameter(name = "vendorId", description = "매입처 ID", example = "201324", required = true)})
    public ApiResponse<VendorResponse> findVendorById(@PathVariable Long vendorId) {
        return ApiResponse.OK(VendorResponse.of(vendorService.findById(vendorId)));
    }

    @PostMapping(value = "/vendors")
    @Operation(summary = "매입처 생성", description = "매입처를 생성한다.")
    public ResponseEntity<ApiResponse<String>> createVendor(@RequestBody @Valid VendorRequest vendorRequest) {
        return ApiResponse.CREATED(
                linkTo(methodOn(ManagementApi.class, findVendorById(vendorService.create(vendorRequest).getId()))).toUri());
    }

    @PutMapping(value = "/vendors/{vendorId}")
    @Operation(summary = "매입처 수정", description = "매입처를 수정한다.")
    @Parameters({@Parameter(name = "vendorId", description = "매입처 ID", example = "201324", required = true)})
    public ApiResponse<String> updateVendor(@PathVariable Long vendorId,
                                            @RequestBody @Valid VendorRequest vendorUpdateRequest) {
        vendorService.update(vendorId, vendorUpdateRequest);
        return ApiResponse.OK;
    }

    @DeleteMapping(value = "/vendors/{vendorId}")
    @Operation(summary = "매입처 삭제", description = "매입처를 삭제한다.")
    @Parameters({@Parameter(name = "vendorId", description = "매입처 ID", example = "201324", required = true)})
    public ApiResponse<String> deleteVendor(@PathVariable Long vendorId) {
        vendorService.removeById(vendorId);
        return ApiResponse.OK;
    }

    // WAREHOUSE
    @GetMapping(value = "/warehouses")
    @Operation(summary = "창고 목록 조회", description = "창고 목록을 조회한다.")
    public ApiResponse<List<WarehouseResponse>> findAllWarehouses() {
        return ApiResponse.OK(warehouseService.findAll()
                .stream()
                .map(WarehouseResponse::of)
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/warehouses/names")
    @Operation(summary = "창고 이름 목록 조회", description = "창고 이름의 목록을 조회한다.")
    public ApiResponse<List<WarehouseSimpleResponse>> findAllWarehouseNames() {
        return ApiResponse.OK(warehouseService.findAllWarehouseNames());
    }

    @GetMapping(value = "/warehouses/{warehouseId}")
    @Operation(summary = "창고 상세 조회", description = "창고의 상세 정보를 조회한다.")
    @Parameters({@Parameter(name = "warehouseId", description = "창고 ID", example = "201324", required = true)})
    public ApiResponse<WarehouseResponse> findWarehouseById(@PathVariable Long warehouseId) {
        return ApiResponse.OK(WarehouseResponse.of(warehouseService.findById(warehouseId)));
    }

    @PostMapping(value = "/warehouses")
    @Operation(summary = "창고 생성", description = "창고를 생성한다.")
    public ResponseEntity<ApiResponse<String>> createWarehouse(@RequestBody @Valid WarehouseRequest warehouseRequest) {
        return ApiResponse.CREATED(
                linkTo(methodOn(ManagementApi.class, findWarehouseById(warehouseService.create(warehouseRequest).getId()))).toUri());
    }

    @PutMapping(value = "/warehouses/{warehouseId}")
    @Operation(summary = "창고 수정", description = "창고를 수정한다.")
    @Parameters({@Parameter(name = "warehouseId", description = "창고 ID", example = "201324", required = true)})
    public ApiResponse<String> updateWarehouse(@PathVariable Long warehouseId,
                                               @RequestBody @Valid WarehouseRequest warehouseUpdateRequest) {
        warehouseService.update(warehouseId, warehouseUpdateRequest);
        return ApiResponse.OK;
    }

    @DeleteMapping(value = "/warehouses/{warehouseId}")
    @Operation(summary = "창고 삭제", description = "창고를 삭제한다.")
    @Parameters({@Parameter(name = "warehouseId", description = "창고 ID", example = "201324", required = true)})
    public ApiResponse<String> deleteWarehouse(@PathVariable Long warehouseId) {
        warehouseService.removeById(warehouseId);
        return ApiResponse.OK;
    }
}
