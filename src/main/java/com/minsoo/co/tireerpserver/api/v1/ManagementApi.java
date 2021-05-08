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
import com.minsoo.co.tireerpserver.service.BrandService;
import com.minsoo.co.tireerpserver.service.VendorService;
import com.minsoo.co.tireerpserver.service.WarehouseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
        return ApiResponse.createOK(brandService.findAll());
    }

    @GetMapping(value = "/brands/names")
    @Operation(summary = "브랜드 이름 목록 조회", description = "브랜드 이름의 목록을 조회한다.")
    public ApiResponse<List<BrandSimpleResponse>> findAllBrandNames() {
        return ApiResponse.createOK(brandService.findAllBrandNames());
    }

    @GetMapping(value = "/brands/{brandId}")
    @Operation(summary = "브랜드 상세 조회", description = "브랜드의 상세 정보를 조회한다.")
    @Parameters({@Parameter(name = "brandId", description = "브랜드 ID", example = "201324", required = true)})
    public ApiResponse<BrandResponse> findBrandById(@PathVariable Long brandId) {
        return ApiResponse.createOK(brandService.findById(brandId));
    }

    @PostMapping(value = "/brands")
    @Operation(summary = "브랜드 생성", description = "브랜드를 생성한다.")
    public ApiResponse<BrandResponse> createBrand(@RequestBody @Valid BrandRequest brandRequest) {
        return ApiResponse.createOK(brandService.create(brandRequest));
    }

    @PutMapping(value = "/brands/{brandId}")
    @Operation(summary = "브랜드 수정", description = "브랜드를 수정한다.")
    @Parameters({@Parameter(name = "brandId", description = "브랜드 ID", example = "201324", required = true)})
    public ApiResponse<BrandResponse> updateBrand(@PathVariable Long brandId,
                                                  @RequestBody @Valid BrandRequest brandUpdateRequest) {
        return ApiResponse.createOK(brandService.update(brandId, brandUpdateRequest));
    }

    @DeleteMapping(value = "/brands/{brandId}")
    @Operation(summary = "브랜드 삭제", description = "브랜드를 삭제한다.")
    @Parameters({@Parameter(name = "brandId", description = "브랜드 ID", example = "201324", required = true)})
    public ApiResponse<String> deleteBrand(@PathVariable Long brandId) {
        brandService.removeById(brandId);
        return ApiResponse.DEFAULT_OK;
    }

    // VENDOR
    @GetMapping(value = "/vendors")
    @Operation(summary = "매입처 목록 조회", description = "매입처 목록을 조회한다.")
    public ApiResponse<List<VendorResponse>> findAllVendors() {
        return ApiResponse.createOK(vendorService.findAll());
    }

    @GetMapping(value = "/vendors/names")
    @Operation(summary = "매입처 이름 목록 조회", description = "매입처 이름의 목록을 조회한다.")
    public ApiResponse<List<VendorSimpleResponse>> findAllVendorNames() {
        return ApiResponse.createOK(vendorService.findAllVendorNames());
    }

    @GetMapping(value = "/vendors/{vendorId}")
    @Operation(summary = "매입처 상세 조회", description = "매입처의 상세 정보를 조회한다.")
    @Parameters({@Parameter(name = "vendorId", description = "매입처 ID", example = "201324", required = true)})
    public ApiResponse<VendorResponse> findVendorById(@PathVariable Long vendorId) {
        return ApiResponse.createOK(vendorService.findById(vendorId));
    }

    @PostMapping(value = "/vendors")
    @Operation(summary = "매입처 생성", description = "매입처를 생성한다.")
    public ApiResponse<VendorResponse> createVendor(@RequestBody @Valid VendorRequest vendorRequest) {
        return ApiResponse.createOK(vendorService.create(vendorRequest));
    }

    @PutMapping(value = "/vendors/{vendorId}")
    @Operation(summary = "매입처 수정", description = "매입처를 수정한다.")
    @Parameters({@Parameter(name = "vendorId", description = "매입처 ID", example = "201324", required = true)})
    public ApiResponse<VendorResponse> updateVendor(@PathVariable Long vendorId,
                                                    @RequestBody @Valid VendorRequest vendorUpdateRequest) {
        return ApiResponse.createOK(vendorService.update(vendorId, vendorUpdateRequest));
    }

    @DeleteMapping(value = "/vendors/{vendorId}")
    @Operation(summary = "매입처 삭제", description = "매입처를 삭제한다.")
    @Parameters({@Parameter(name = "vendorId", description = "매입처 ID", example = "201324", required = true)})
    public ApiResponse<String> deleteVendor(@PathVariable Long vendorId) {
        vendorService.removeById(vendorId);
        return ApiResponse.DEFAULT_OK;
    }

    // WAREHOUSE
    @GetMapping(value = "/warehouses")
    @Operation(summary = "창고 목록 조회", description = "창고 목록을 조회한다.")
    public ApiResponse<List<WarehouseResponse>> findAllWarehouses() {
        return ApiResponse.createOK(warehouseService.findAll());
    }

    @GetMapping(value = "/warehouses/names")
    @Operation(summary = "창고 이름 목록 조회", description = "창고 이름의 목록을 조회한다.")
    public ApiResponse<List<WarehouseSimpleResponse>> findAllWarehouseNames() {
        return ApiResponse.createOK(warehouseService.findAllWarehouseNames());
    }

    @GetMapping(value = "/warehouses/{warehouseId}")
    @Operation(summary = "창고 상세 조회", description = "창고의 상세 정보를 조회한다.")
    @Parameters({@Parameter(name = "warehouseId", description = "창고 ID", example = "201324", required = true)})
    public ApiResponse<WarehouseResponse> findWarehouseById(@PathVariable Long warehouseId) {
        return ApiResponse.createOK(warehouseService.findById(warehouseId));
    }

    @PostMapping(value = "/warehouses")
    @Operation(summary = "창고 생성", description = "창고를 생성한다.")
    public ApiResponse<WarehouseResponse> createWarehouse(@RequestBody @Valid WarehouseRequest warehouseRequest) {
        return ApiResponse.createOK(warehouseService.create(warehouseRequest));
    }

    @PutMapping(value = "/warehouses/{warehouseId}")
    @Operation(summary = "창고 수정", description = "창고를 수정한다.")
    @Parameters({@Parameter(name = "warehouseId", description = "창고 ID", example = "201324", required = true)})
    public ApiResponse<WarehouseResponse> updateWarehouse(@PathVariable Long warehouseId,
                                                          @RequestBody @Valid WarehouseRequest warehouseUpdateRequest) {
        return ApiResponse.createOK(warehouseService.update(warehouseId, warehouseUpdateRequest));
    }

    @DeleteMapping(value = "/warehouses/{warehouseId}")
    @Operation(summary = "창고 삭제", description = "창고를 삭제한다.")
    @Parameters({@Parameter(name = "warehouseId", description = "창고 ID", example = "201324", required = true)})
    public ApiResponse<String> deleteWarehouse(@PathVariable Long warehouseId) {
        warehouseService.removeById(warehouseId);
        return ApiResponse.DEFAULT_OK;
    }
}
