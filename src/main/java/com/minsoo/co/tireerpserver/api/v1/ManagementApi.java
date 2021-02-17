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
    public ApiResponseDTO<List<BrandResponse>> findAllBrands() {
        return ApiResponseDTO.createOK(brandService.findAll()
                .stream()
                .map(BrandResponse::of)
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/brands/names")
    public ApiResponseDTO<List<String>> findAllBrandNames() {
        return ApiResponseDTO.createOK(brandService.findAllBrandNames());
    }

    @GetMapping(value = "/brands/{brandId}")
    public ApiResponseDTO<BrandResponse> findBrandById(@PathVariable Long brandId) {
        return ApiResponseDTO.createOK(BrandResponse.of(brandService.findById(brandId)));
    }

    @PostMapping(value = "/brands")
    public ApiResponseDTO<BrandResponse> createBrand(@RequestBody @Valid BrandRequest brandRequest) {
        return ApiResponseDTO.createOK(BrandResponse.of(brandService.create(brandRequest)));
    }

    @PutMapping(value = "/brands/{brandId}")
    public ApiResponseDTO<BrandResponse> updateBrand(@PathVariable Long brandId,
                                                     @RequestBody @Valid BrandRequest brandUpdateRequest) {
        return ApiResponseDTO.createOK(BrandResponse.of(brandService.update(brandId, brandUpdateRequest)));
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

    @GetMapping(value = "/vendors/names")
    public ApiResponseDTO<List<String>> findAllVendorNames() {
        return ApiResponseDTO.createOK(vendorService.findAllVendorNames());
    }

    @GetMapping(value = "/vendors/{vendorId}")
    public ApiResponseDTO<VendorResponse> findVendorById(@PathVariable Long vendorId) {
        return ApiResponseDTO.createOK(VendorResponse.of(vendorService.findById(vendorId)));
    }

    @PostMapping(value = "/vendors")
    public ApiResponseDTO<VendorResponse> createVendor(@RequestBody @Valid VendorRequest vendorRequest) {
        return ApiResponseDTO.createOK(VendorResponse.of(vendorService.create(vendorRequest)));
    }

    @PutMapping(value = "/vendors/{vendorId}")
    public ApiResponseDTO<VendorResponse> updateVendor(@PathVariable Long vendorId,
                                                       @RequestBody @Valid VendorRequest vendorUpdateRequest) {
        return ApiResponseDTO.createOK(VendorResponse.of(vendorService.update(vendorId, vendorUpdateRequest)));
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

    @GetMapping(value = "/warehouses/names")
    public ApiResponseDTO<List<String>> findAllWarehouseNames() {
        return ApiResponseDTO.createOK(warehouseService.findAllWarehouseNames());
    }

    @GetMapping(value = "/warehouses/{warehouseId}")
    public ApiResponseDTO<WarehouseResponse> findWarehouseById(@PathVariable Long warehouseId) {
        return ApiResponseDTO.createOK(WarehouseResponse.of(warehouseService.findById(warehouseId)));
    }

    @PostMapping(value = "/warehouses")
    public ApiResponseDTO<WarehouseResponse> createWarehouse(@RequestBody @Valid WarehouseRequest warehouseRequest) {
        return ApiResponseDTO.createOK(WarehouseResponse.of(warehouseService.create(warehouseRequest)));
    }

    @PutMapping(value = "/warehouses/{warehouseId}")
    public ApiResponseDTO<WarehouseResponse> updateWarehouse(@PathVariable Long warehouseId,
                                                             @RequestBody @Valid WarehouseRequest warehouseUpdateRequest) {
        return ApiResponseDTO.createOK(WarehouseResponse.of(warehouseService.update(warehouseId, warehouseUpdateRequest)));
    }

    @DeleteMapping(value = "/warehouses/{warehouseId}")
    public ApiResponseDTO<String> deleteWarehouse(@PathVariable Long warehouseId) {
        warehouseService.remove(warehouseId);
        return ApiResponseDTO.DEFAULT_OK;
    }
}
