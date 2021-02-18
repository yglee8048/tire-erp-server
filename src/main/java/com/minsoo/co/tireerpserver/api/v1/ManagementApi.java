package com.minsoo.co.tireerpserver.api.v1;

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
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

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
    @ApiOperation(value = "브랜드 목록 조회", notes = "브랜드 목록을 조회한다.")
    public ApiResponse<List<BrandResponse>> findAllBrands() {
        return ApiResponse.createOK(brandService.findAll()
                .stream()
                .map(BrandResponse::of)
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/brands/names")
    @ApiOperation(value = "브랜드 이름 목록 조회", notes = "브랜드 이름의 목록을 조회한다.")
    public ApiResponse<List<String>> findAllBrandNames() {
        return ApiResponse.createOK(brandService.findAllBrandNames());
    }

    @GetMapping(value = "/brands/{brandId}")
    @ApiOperation(value = "브랜드 상세 조회", notes = "브랜드의 상세 정보를 조회한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "brandId", value = "브랜드 ID", example = "201324", required = true)})
    public ApiResponse<BrandResponse> findBrandById(@PathVariable Long brandId) {
        return ApiResponse.createOK(BrandResponse.of(brandService.findById(brandId)));
    }

    @PostMapping(value = "/brands")
    @ApiOperation(value = "브랜드 생성", notes = "브랜드를 생성한다.")
    public ApiResponse<BrandResponse> createBrand(@RequestBody @Valid BrandRequest brandRequest) {
        return ApiResponse.createOK(BrandResponse.of(brandService.create(brandRequest)));
    }

    @PutMapping(value = "/brands/{brandId}")
    @ApiOperation(value = "브랜드 수정", notes = "브랜드를 수정한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "brandId", value = "브랜드 ID", example = "201324", required = true)})
    public ApiResponse<BrandResponse> updateBrand(@PathVariable Long brandId,
                                                  @RequestBody @Valid BrandRequest brandUpdateRequest) {
        return ApiResponse.createOK(BrandResponse.of(brandService.update(brandId, brandUpdateRequest)));
    }

    @DeleteMapping(value = "/brands/{brandId}")
    @ApiOperation(value = "브랜드 삭제", notes = "브랜드를 삭제한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "brandId", value = "브랜드 ID", example = "201324", required = true)})
    public ApiResponse<String> deleteBrand(@PathVariable Long brandId) {
        brandService.remove(brandId);
        return ApiResponse.DEFAULT_OK;
    }

    // VENDOR
    @GetMapping(value = "/vendors")
    @ApiOperation(value = "매입처 목록 조회", notes = "매입처 목록을 조회한다.")
    public ApiResponse<List<VendorResponse>> findAllVendors() {
        return ApiResponse.createOK(vendorService.findAll()
                .stream()
                .map(VendorResponse::of)
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/vendors/names")
    @ApiOperation(value = "매입처 이름 목록 조회", notes = "매입처 이름의 목록을 조회한다.")
    public ApiResponse<List<String>> findAllVendorNames() {
        return ApiResponse.createOK(vendorService.findAllVendorNames());
    }

    @GetMapping(value = "/vendors/{vendorId}")
    @ApiOperation(value = "매입처 상세 조회", notes = "매입처의 상세 정보를 조회한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vendorId", value = "매입처 ID", example = "201324", required = true)})
    public ApiResponse<VendorResponse> findVendorById(@PathVariable Long vendorId) {
        return ApiResponse.createOK(VendorResponse.of(vendorService.findById(vendorId)));
    }

    @PostMapping(value = "/vendors")
    @ApiOperation(value = "매입처 생성", notes = "매입처를 생성한다.")
    public ApiResponse<VendorResponse> createVendor(@RequestBody @Valid VendorRequest vendorRequest) {
        return ApiResponse.createOK(VendorResponse.of(vendorService.create(vendorRequest)));
    }

    @PutMapping(value = "/vendors/{vendorId}")
    @ApiOperation(value = "매입처 수정", notes = "매입처를 수정한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vendorId", value = "매입처 ID", example = "201324", required = true)})
    public ApiResponse<VendorResponse> updateVendor(@PathVariable Long vendorId,
                                                    @RequestBody @Valid VendorRequest vendorUpdateRequest) {
        return ApiResponse.createOK(VendorResponse.of(vendorService.update(vendorId, vendorUpdateRequest)));
    }

    @DeleteMapping(value = "/vendors/{vendorId}")
    @ApiOperation(value = "매입처 삭제", notes = "매입처를 삭제한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vendorId", value = "매입처 ID", example = "201324", required = true)})
    public ApiResponse<String> deleteVendor(@PathVariable Long vendorId) {
        vendorService.remove(vendorId);
        return ApiResponse.DEFAULT_OK;
    }

    // WAREHOUSE
    @GetMapping(value = "/warehouses")
    @ApiOperation(value = "창고 목록 조회", notes = "창고 목록을 조회한다.")
    public ApiResponse<List<WarehouseResponse>> findAllWarehouses() {
        return ApiResponse.createOK(warehouseService.findAll()
                .stream()
                .map(WarehouseResponse::of)
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/warehouses/names")
    @ApiOperation(value = "창고 이름 목록 조회", notes = "창고 이름의 목록을 조회한다.")
    public ApiResponse<List<String>> findAllWarehouseNames() {
        return ApiResponse.createOK(warehouseService.findAllWarehouseNames());
    }

    @GetMapping(value = "/warehouses/{warehouseId}")
    @ApiOperation(value = "창고 상세 조회", notes = "창고의 상세 정보를 조회한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "warehouseId", value = "창고 ID", example = "201324", required = true)})
    public ApiResponse<WarehouseResponse> findWarehouseById(@PathVariable Long warehouseId) {
        return ApiResponse.createOK(WarehouseResponse.of(warehouseService.findById(warehouseId)));
    }

    @PostMapping(value = "/warehouses")
    @ApiOperation(value = "창고 생성", notes = "창고를 생성한다.")
    public ApiResponse<WarehouseResponse> createWarehouse(@RequestBody @Valid WarehouseRequest warehouseRequest) {
        return ApiResponse.createOK(WarehouseResponse.of(warehouseService.create(warehouseRequest)));
    }

    @PutMapping(value = "/warehouses/{warehouseId}")
    @ApiOperation(value = "창고 수정", notes = "창고를 수정한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "warehouseId", value = "창고 ID", example = "201324", required = true)})
    public ApiResponse<WarehouseResponse> updateWarehouse(@PathVariable Long warehouseId,
                                                          @RequestBody @Valid WarehouseRequest warehouseUpdateRequest) {
        return ApiResponse.createOK(WarehouseResponse.of(warehouseService.update(warehouseId, warehouseUpdateRequest)));
    }

    @DeleteMapping(value = "/warehouses/{warehouseId}")
    @ApiOperation(value = "창고 삭제", notes = "창고를 삭제한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "warehouseId", value = "창고 ID", example = "201324", required = true)})
    public ApiResponse<String> deleteWarehouse(@PathVariable Long warehouseId) {
        warehouseService.remove(warehouseId);
        return ApiResponse.DEFAULT_OK;
    }
}
