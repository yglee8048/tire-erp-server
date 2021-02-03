package com.minsoo.co.tireerpserver.api.v1.admin;

import com.minsoo.co.tireerpserver.model.dto.ResponseDTO;
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
import org.springframework.http.HttpStatus;
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
    public ResponseDTO<List<BrandResponse>> findAllBrands() {
        return new ResponseDTO<>(brandService.findAll()
                .stream()
                .map(BrandResponse::new)
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/brands/name")
    public ResponseDTO<List<String>> findAllBrandNames() {
        return new ResponseDTO<>(brandService.findAllBrandNames());
    }

    @GetMapping(value = "/brands/{brandId}")
    public ResponseDTO<BrandResponse> findBrandById(@PathVariable Long brandId) {
        return new ResponseDTO<>(new BrandResponse(brandService.findById(brandId)));
    }

    @PostMapping(value = "/brands")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO<BrandResponse> createBrand(@RequestBody @Valid BrandCreateRequest brandCreateRequest) {
        return new ResponseDTO<>(new BrandResponse(brandService.create(brandCreateRequest)));
    }

    @PutMapping(value = "/brands")
    public ResponseDTO<BrandResponse> updateBrand(@RequestBody @Valid BrandUpdateRequest brandUpdateRequest) {
        return new ResponseDTO<>(new BrandResponse(brandService.update(brandUpdateRequest)));
    }

    @DeleteMapping(value = "/brands/{brandId}")
    public ResponseDTO<Long> deleteBrand(@PathVariable Long brandId) {
        brandService.remove(brandId);
        return new ResponseDTO<>(brandId);
    }

    // VENDOR
    @GetMapping(value = "/vendors")
    public ResponseDTO<List<VendorResponse>> findAllVendors() {
        return new ResponseDTO<>(vendorService.findAll()
                .stream()
                .map(VendorResponse::new)
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/vendors/name")
    public ResponseDTO<List<String>> findAllVendorNames() {
        return new ResponseDTO<>(vendorService.findAllVendorNames());
    }

    @GetMapping(value = "/vendors/{vendorId}")
    public ResponseDTO<VendorResponse> findVendorById(@PathVariable Long vendorId) {
        return new ResponseDTO<>(new VendorResponse(vendorService.findById(vendorId)));
    }

    @PostMapping(value = "/vendors")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO<VendorResponse> createVendor(@RequestBody @Valid VendorCreateRequest vendorCreateRequest) {
        return new ResponseDTO<>(new VendorResponse(vendorService.create(vendorCreateRequest)));
    }

    @PutMapping(value = "/vendors")
    public ResponseDTO<VendorResponse> updateVendor(@RequestBody @Valid VendorUpdateRequest vendorUpdateRequest) {
        return new ResponseDTO<>(new VendorResponse(vendorService.update(vendorUpdateRequest)));
    }

    @DeleteMapping(value = "/vendors/{vendorId}")
    public ResponseDTO<Long> deleteVendor(@PathVariable Long vendorId) {
        vendorService.remove(vendorId);
        return new ResponseDTO<>(vendorId);
    }

    // WAREHOUSE
    @GetMapping(value = "/warehouses")
    public ResponseDTO<List<WarehouseResponse>> findAllWarehouses() {
        return new ResponseDTO<>(warehouseService.findAll()
                .stream()
                .map(WarehouseResponse::new)
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/warehouses/name")
    public ResponseDTO<List<String>> findAllWarehouseNames() {
        return new ResponseDTO<>(warehouseService.findAllWarehouseNames());
    }

    @GetMapping(value = "/warehouses/{warehouseId}")
    public ResponseDTO<WarehouseResponse> findWarehouseById(@PathVariable Long warehouseId) {
        return new ResponseDTO<>(new WarehouseResponse(warehouseService.findById(warehouseId)));
    }

    @PostMapping(value = "/warehouses")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO<WarehouseResponse> createWarehouse(@RequestBody @Valid WarehouseCreateRequest warehouseCreateRequest) {
        return new ResponseDTO<>(new WarehouseResponse(warehouseService.create(warehouseCreateRequest)));
    }

    @PutMapping(value = "/warehouses")
    public ResponseDTO<WarehouseResponse> updateWarehouse(@RequestBody @Valid WarehouseUpdateRequest warehouseUpdateRequest) {
        return new ResponseDTO<>(new WarehouseResponse(warehouseService.update(warehouseUpdateRequest)));
    }

    @DeleteMapping(value = "/warehouses/{warehouseId}")
    public ResponseDTO<Long> deleteWarehouse(@PathVariable Long warehouseId) {
        warehouseService.remove(warehouseId);
        return new ResponseDTO<>(warehouseId);
    }
}
