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
        List<BrandResponse> brandResponses = brandService.findAll().stream()
                .map(BrandResponse::new)
                .collect(Collectors.toList());
        return new ResponseDTO<>(brandResponses);
    }

    @GetMapping(value = "/brands/{brandId}")
    public ResponseDTO<BrandResponse> findBrandById(@PathVariable Long brandId) {
        BrandResponse brandResponse = new BrandResponse(brandService.findById(brandId));
        return new ResponseDTO<>(brandResponse);
    }

    @PostMapping(value = "/brands")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO<BrandResponse> createBrand(@RequestBody @Valid BrandCreateRequest brandCreateRequest) {
        BrandResponse brandResponse = new BrandResponse(brandService.create(brandCreateRequest));
        return new ResponseDTO<>(brandResponse);
    }

    @PutMapping(value = "/brands")
    public ResponseDTO<BrandResponse> updateBrand(@RequestBody @Valid BrandUpdateRequest brandUpdateRequest) {
        BrandResponse brandResponse = new BrandResponse(brandService.update(brandUpdateRequest));
        return new ResponseDTO<>(brandResponse);
    }

    @DeleteMapping(value = "/brands/{brandId}")
    public void deleteBrand(@PathVariable Long brandId) {
        brandService.remove(brandId);
    }

    // VENDOR
    @GetMapping(value = "/vendors")
    public ResponseDTO<List<VendorResponse>> findAllVendors() {
        List<VendorResponse> vendorResponses = vendorService.findAll().stream()
                .map(VendorResponse::new)
                .collect(Collectors.toList());
        return new ResponseDTO<>(vendorResponses);
    }

    @GetMapping(value = "/vendors/{vendorId}")
    public ResponseDTO<VendorResponse> findVendorById(@PathVariable Long vendorId) {
        VendorResponse vendorResponse = new VendorResponse(vendorService.findById(vendorId));
        return new ResponseDTO<>(vendorResponse);
    }

    @PostMapping(value = "/vendors")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO<VendorResponse> createVendor(@RequestBody @Valid VendorCreateRequest vendorCreateRequest) {
        VendorResponse vendorResponse = new VendorResponse(vendorService.create(vendorCreateRequest));
        return new ResponseDTO<>(vendorResponse);
    }

    @PutMapping(value = "/vendors")
    public ResponseDTO<VendorResponse> updateVendor(@RequestBody @Valid VendorUpdateRequest vendorUpdateRequest) {
        VendorResponse vendorResponse = new VendorResponse(vendorService.update(vendorUpdateRequest));
        return new ResponseDTO<>(vendorResponse);
    }

    @DeleteMapping(value = "/vendors/{vendorId}")
    public void deleteVendor(@PathVariable Long vendorId) {
        vendorService.remove(vendorId);
    }

    // WAREHOUSE
    @GetMapping(value = "/warehouses")
    public ResponseDTO<List<WarehouseResponse>> findAllWarehouses() {
        List<WarehouseResponse> warehouseResponses = warehouseService.findAll().stream()
                .map(WarehouseResponse::new)
                .collect(Collectors.toList());
        return new ResponseDTO<>(warehouseResponses);
    }

    @GetMapping(value = "/warehouses/{warehouseId}")
    public ResponseDTO<WarehouseResponse> findWarehouseById(@PathVariable Long warehouseId) {
        WarehouseResponse warehouseResponse = new WarehouseResponse(warehouseService.findById(warehouseId));
        return new ResponseDTO<>(warehouseResponse);
    }

    @PostMapping(value = "/warehouses")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO<WarehouseResponse> createWarehouse(@RequestBody @Valid WarehouseCreateRequest warehouseCreateRequest) {
        WarehouseResponse warehouseResponse = new WarehouseResponse(warehouseService.create(warehouseCreateRequest));
        return new ResponseDTO<>(warehouseResponse);
    }

    @PutMapping(value = "/warehouses")
    public ResponseDTO<WarehouseResponse> updateWarehouse(@RequestBody @Valid WarehouseUpdateRequest warehouseUpdateRequest) {
        WarehouseResponse warehouseResponse = new WarehouseResponse(warehouseService.update(warehouseUpdateRequest));
        return new ResponseDTO<>(warehouseResponse);
    }

    @DeleteMapping(value = "/warehouses/{warehouseId}")
    public void deleteWarehouse(@PathVariable Long warehouseId) {
        warehouseService.remove(warehouseId);
    }
}
