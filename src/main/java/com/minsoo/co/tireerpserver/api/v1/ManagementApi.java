package com.minsoo.co.tireerpserver.api.v1;

import com.minsoo.co.tireerpserver.model.dto.response.ApiResponseDTO;
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
    @ApiOperation(value = "제조사 목록 조회", notes = "제조사 목록을 조회한다.")
    public ApiResponseDTO<List<BrandResponse>> findAllBrands() {
        return ApiResponseDTO.createOK(brandService.findAll()
                .stream()
                .map(BrandResponse::of)
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/brands/names")
    @ApiOperation(value = "제조사 이름 목록 조회", notes = "제조사 이름의 목록을 조회한다.")
    public ApiResponseDTO<List<String>> findAllBrandNames() {
        return ApiResponseDTO.createOK(brandService.findAllBrandNames());
    }

    @GetMapping(value = "/brands/{brandId}")
    @ApiOperation(value = "제조사 상세 조회", notes = "제조사의 상세 정보를 조회한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "brandId", value = "제조사 ID", example = "201324", required = true)})
    public ApiResponseDTO<BrandResponse> findBrandById(@PathVariable Long brandId) {
        return ApiResponseDTO.createOK(BrandResponse.of(brandService.findById(brandId)));
    }

    @PostMapping(value = "/brands")
    @ApiOperation(value = "제조사 생성", notes = "제조사를 생성한다.")
    public ApiResponseDTO<BrandResponse> createBrand(@RequestBody @Valid BrandRequest brandRequest) {
        return ApiResponseDTO.createOK(BrandResponse.of(brandService.create(brandRequest)));
    }

    @PutMapping(value = "/brands/{brandId}")
    @ApiOperation(value = "제조사 수정", notes = "제조사를 수정한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "brandId", value = "제조사 ID", example = "201324", required = true)})
    public ApiResponseDTO<BrandResponse> updateBrand(@PathVariable Long brandId,
                                                     @RequestBody @Valid BrandRequest brandUpdateRequest) {
        return ApiResponseDTO.createOK(BrandResponse.of(brandService.update(brandId, brandUpdateRequest)));
    }

    @DeleteMapping(value = "/brands/{brandId}")
    @ApiOperation(value = "제조사 삭제", notes = "제조사를 삭제한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "brandId", value = "제조사 ID", example = "201324", required = true)})
    public ApiResponseDTO<String> deleteBrand(@PathVariable Long brandId) {
        brandService.remove(brandId);
        return ApiResponseDTO.DEFAULT_OK;
    }

    // VENDOR
    @GetMapping(value = "/vendors")
    @ApiOperation(value = "매입처 목록 조회", notes = "매입처 목록을 조회한다.")
    public ApiResponseDTO<List<VendorResponse>> findAllVendors() {
        return ApiResponseDTO.createOK(vendorService.findAll()
                .stream()
                .map(VendorResponse::of)
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/vendors/names")
    @ApiOperation(value = "매입처 이름 목록 조회", notes = "매입처 이름의 목록을 조회한다.")
    public ApiResponseDTO<List<String>> findAllVendorNames() {
        return ApiResponseDTO.createOK(vendorService.findAllVendorNames());
    }

    @GetMapping(value = "/vendors/{vendorId}")
    @ApiOperation(value = "매입처 상세 조회", notes = "매입처의 상세 정보를 조회한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vendorId", value = "매입처 ID", example = "201324", required = true)})
    public ApiResponseDTO<VendorResponse> findVendorById(@PathVariable Long vendorId) {
        return ApiResponseDTO.createOK(VendorResponse.of(vendorService.findById(vendorId)));
    }

    @PostMapping(value = "/vendors")
    @ApiOperation(value = "매입처 생성", notes = "매입처를 생성한다.")
    public ApiResponseDTO<VendorResponse> createVendor(@RequestBody @Valid VendorRequest vendorRequest) {
        return ApiResponseDTO.createOK(VendorResponse.of(vendorService.create(vendorRequest)));
    }

    @PutMapping(value = "/vendors/{vendorId}")
    @ApiOperation(value = "매입처 수정", notes = "매입처를 수정한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vendorId", value = "매입처 ID", example = "201324", required = true)})
    public ApiResponseDTO<VendorResponse> updateVendor(@PathVariable Long vendorId,
                                                       @RequestBody @Valid VendorRequest vendorUpdateRequest) {
        return ApiResponseDTO.createOK(VendorResponse.of(vendorService.update(vendorId, vendorUpdateRequest)));
    }

    @DeleteMapping(value = "/vendors/{vendorId}")
    @ApiOperation(value = "매입처 삭제", notes = "매입처를 삭제한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vendorId", value = "매입처 ID", example = "201324", required = true)})
    public ApiResponseDTO<String> deleteVendor(@PathVariable Long vendorId) {
        vendorService.remove(vendorId);
        return ApiResponseDTO.DEFAULT_OK;
    }

    // WAREHOUSE
    @GetMapping(value = "/warehouses")
    @ApiOperation(value = "창고 목록 조회", notes = "창고 목록을 조회한다.")
    public ApiResponseDTO<List<WarehouseResponse>> findAllWarehouses() {
        return ApiResponseDTO.createOK(warehouseService.findAll()
                .stream()
                .map(WarehouseResponse::of)
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/warehouses/names")
    @ApiOperation(value = "창고 이름 목록 조회", notes = "창고 이름의 목록을 조회한다.")
    public ApiResponseDTO<List<String>> findAllWarehouseNames() {
        return ApiResponseDTO.createOK(warehouseService.findAllWarehouseNames());
    }

    @GetMapping(value = "/warehouses/{warehouseId}")
    @ApiOperation(value = "창고 상세 조회", notes = "창고의 상세 정보를 조회한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "warehouseId", value = "창고 ID", example = "201324", required = true)})
    public ApiResponseDTO<WarehouseResponse> findWarehouseById(@PathVariable Long warehouseId) {
        return ApiResponseDTO.createOK(WarehouseResponse.of(warehouseService.findById(warehouseId)));
    }

    @PostMapping(value = "/warehouses")
    @ApiOperation(value = "창고 생성", notes = "창고를 생성한다.")
    public ApiResponseDTO<WarehouseResponse> createWarehouse(@RequestBody @Valid WarehouseRequest warehouseRequest) {
        return ApiResponseDTO.createOK(WarehouseResponse.of(warehouseService.create(warehouseRequest)));
    }

    @PutMapping(value = "/warehouses/{warehouseId}")
    @ApiOperation(value = "창고 수정", notes = "창고를 수정한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "warehouseId", value = "창고 ID", example = "201324", required = true)})
    public ApiResponseDTO<WarehouseResponse> updateWarehouse(@PathVariable Long warehouseId,
                                                             @RequestBody @Valid WarehouseRequest warehouseUpdateRequest) {
        return ApiResponseDTO.createOK(WarehouseResponse.of(warehouseService.update(warehouseId, warehouseUpdateRequest)));
    }

    @DeleteMapping(value = "/warehouses/{warehouseId}")
    @ApiOperation(value = "창고 삭제", notes = "창고를 삭제한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "warehouseId", value = "창고 ID", example = "201324", required = true)})
    public ApiResponseDTO<String> deleteWarehouse(@PathVariable Long warehouseId) {
        warehouseService.remove(warehouseId);
        return ApiResponseDTO.DEFAULT_OK;
    }
}
