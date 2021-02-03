package com.minsoo.co.tireerpserver.api.v1.admin;

import com.minsoo.co.tireerpserver.model.dto.response.ApiResponseDTO;
import com.minsoo.co.tireerpserver.model.dto.management.brand.BrandCreateRequest;
import com.minsoo.co.tireerpserver.model.dto.management.brand.BrandResponse;
import com.minsoo.co.tireerpserver.model.dto.management.brand.BrandUpdateRequest;
import com.minsoo.co.tireerpserver.model.dto.management.vendor.VendorCreateRequest;
import com.minsoo.co.tireerpserver.model.dto.management.vendor.VendorResponse;
import com.minsoo.co.tireerpserver.model.dto.management.vendor.VendorUpdateRequest;
import com.minsoo.co.tireerpserver.model.dto.management.warehouse.WarehouseCreateRequest;
import com.minsoo.co.tireerpserver.model.dto.management.warehouse.WarehouseResponse;
import com.minsoo.co.tireerpserver.model.dto.management.warehouse.WarehouseUpdateRequest;
import com.minsoo.co.tireerpserver.service.BrandService;
import com.minsoo.co.tireerpserver.service.VendorService;
import com.minsoo.co.tireerpserver.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/admin/api/v1")
@RequiredArgsConstructor
public class ManagementApi {

    private final BrandService brandService;
    private final VendorService vendorService;
    private final WarehouseService warehouseService;

    // BRAND
    @GetMapping(value = "/brands")
    public ApiResponseDTO<List<BrandResponse>> findAllBrands() {
        return ApiResponseDTO.createOK(brandService.findAll()
                .stream()
                .map(BrandResponse::of)
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/brands/name")
    public ApiResponseDTO<List<String>> findAllBrandNames() {
        return ApiResponseDTO.createOK(brandService.findAllBrandNames());
    }

    @GetMapping(value = "/brands/{brandId}")
    public ApiResponseDTO<BrandResponse> findBrandById(@PathVariable Long brandId) {
        return ApiResponseDTO.createOK(BrandResponse.of(brandService.findById(brandId)));
    }

    @PostMapping(value = "/brands")
    public ApiResponseDTO<BrandResponse> createBrand(@RequestBody @Valid BrandCreateRequest brandCreateRequest) {
        return ApiResponseDTO.createOK(BrandResponse.of(brandService.create(brandCreateRequest)));
    }

    @PutMapping(value = "/brands")
    public ApiResponseDTO<BrandResponse> updateBrand(@RequestBody @Valid BrandUpdateRequest brandUpdateRequest) {
        return ApiResponseDTO.createOK(BrandResponse.of(brandService.update(brandUpdateRequest)));
    }

    @DeleteMapping(value = "/brands/{brandId}")
    public ApiResponseDTO<String> deleteBrand(@PathVariable Long brandId) {
        brandService.remove(brandId);
        return ApiResponseDTO.DEFAULT_OK;
    }

    // VENDOR
    @GetMapping(value = "/vendors")
    public ApiResponseDTO<List<VendorResponse>> findAllVendors() {
        return ApiResponseDTO.createOK(vendorService.findAll()
                .stream()
                .map(VendorResponse::of)
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/vendors/name")
    public ApiResponseDTO<List<String>> findAllVendorNames() {
        return ApiResponseDTO.createOK(vendorService.findAllVendorNames());
    }

    @GetMapping(value = "/vendors/{vendorId}")
    public ApiResponseDTO<VendorResponse> findVendorById(@PathVariable Long vendorId) {
        return ApiResponseDTO.createOK(VendorResponse.of(vendorService.findById(vendorId)));
    }

    @PostMapping(value = "/vendors")
    public ApiResponseDTO<VendorResponse> createVendor(@RequestBody @Valid VendorCreateRequest vendorCreateRequest) {
        return ApiResponseDTO.createOK(VendorResponse.of(vendorService.create(vendorCreateRequest)));
    }

    @PutMapping(value = "/vendors")
    public ApiResponseDTO<VendorResponse> updateVendor(@RequestBody @Valid VendorUpdateRequest vendorUpdateRequest) {
        return ApiResponseDTO.createOK(VendorResponse.of(vendorService.update(vendorUpdateRequest)));
    }

    @DeleteMapping(value = "/vendors/{vendorId}")
    public ApiResponseDTO<String> deleteVendor(@PathVariable Long vendorId) {
        vendorService.remove(vendorId);
        return ApiResponseDTO.DEFAULT_OK;
    }

    // WAREHOUSE
    @GetMapping(value = "/warehouses")
    public ApiResponseDTO<List<WarehouseResponse>> findAllWarehouses() {
        return ApiResponseDTO.createOK(warehouseService.findAll()
                .stream()
                .map(WarehouseResponse::of)
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/warehouses/name")
    public ApiResponseDTO<List<String>> findAllWarehouseNames() {
        return ApiResponseDTO.createOK(warehouseService.findAllWarehouseNames());
    }

    @GetMapping(value = "/warehouses/{warehouseId}")
    public ApiResponseDTO<WarehouseResponse> findWarehouseById(@PathVariable Long warehouseId) {
        return ApiResponseDTO.createOK(WarehouseResponse.of(warehouseService.findById(warehouseId)));
    }

    @PostMapping(value = "/warehouses")
    public ApiResponseDTO<WarehouseResponse> createWarehouse(@RequestBody @Valid WarehouseCreateRequest warehouseCreateRequest) {
        return ApiResponseDTO.createOK(WarehouseResponse.of(warehouseService.create(warehouseCreateRequest)));
    }

    @PutMapping(value = "/warehouses")
    public ApiResponseDTO<WarehouseResponse> updateWarehouse(@RequestBody @Valid WarehouseUpdateRequest warehouseUpdateRequest) {
        return ApiResponseDTO.createOK(WarehouseResponse.of(warehouseService.update(warehouseUpdateRequest)));
    }

    @DeleteMapping(value = "/warehouses/{warehouseId}")
    public ApiResponseDTO<String> deleteWarehouse(@PathVariable Long warehouseId) {
        warehouseService.remove(warehouseId);
        return ApiResponseDTO.DEFAULT_OK;
    }
}
