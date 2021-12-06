package com.minsoo.co.tireerpserver.api;

import com.minsoo.co.tireerpserver.model.ApiResponse;
import com.minsoo.co.tireerpserver.model.request.management.BrandRequest;
import com.minsoo.co.tireerpserver.model.request.management.PatternRequest;
import com.minsoo.co.tireerpserver.model.request.management.VendorRequest;
import com.minsoo.co.tireerpserver.model.request.management.WarehouseRequest;
import com.minsoo.co.tireerpserver.model.response.management.BrandResponse;
import com.minsoo.co.tireerpserver.model.response.management.PatternResponse;
import com.minsoo.co.tireerpserver.model.response.management.VendorResponse;
import com.minsoo.co.tireerpserver.model.response.management.WarehouseResponse;
import com.minsoo.co.tireerpserver.service.management.BrandService;
import com.minsoo.co.tireerpserver.service.management.PatternService;
import com.minsoo.co.tireerpserver.service.management.VendorService;
import com.minsoo.co.tireerpserver.service.management.WarehouseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ManagementApi {

    private final BrandService brandService;
    private final PatternService patternService;
    private final VendorService vendorService;
    private final WarehouseService warehouseService;

    @GetMapping("/brands")
    public ApiResponse<List<BrandResponse>> findAllBrand() {
        return ApiResponse.OK(brandService.findAll()
                .stream()
                .map(BrandResponse::new)
                .collect(Collectors.toList()));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/brands")
    public ApiResponse<BrandResponse> createBrand(@RequestBody BrandRequest brandRequest) {
        return ApiResponse.CREATED(new BrandResponse(brandService.create(brandRequest)));
    }

    @GetMapping("/brands/{brandId}")
    public ApiResponse<BrandResponse> findBrandById(@PathVariable Long brandId) {
        return ApiResponse.OK(new BrandResponse(brandService.findById(brandId)));
    }

    @PutMapping("/brands/{brandId}")
    public ApiResponse<BrandResponse> updateBrand(@PathVariable Long brandId,
                                                  @RequestBody @Valid BrandRequest brandRequest) {
        return ApiResponse.OK(new BrandResponse(brandService.update(brandId, brandRequest)));
    }

    @DeleteMapping("/brands/{brandId}")
    public ApiResponse<Void> deleteBrand(@PathVariable Long brandId) {
        brandService.deleteById(brandId);
        return ApiResponse.NO_CONTENT();
    }

    @GetMapping("/brands/{brandId}/patterns")
    public ApiResponse<List<PatternResponse>> findAllPatterns(@PathVariable Long brandId) {
        return ApiResponse.OK(patternService.findAllByBrandId(brandId)
                .stream()
                .map(PatternResponse::new)
                .collect(Collectors.toList()));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/brands/{brandId}/patterns")
    public ApiResponse<PatternResponse> createPattern(@PathVariable Long brandId,
                                                      @RequestBody @Valid PatternRequest patternRequest) {
        return ApiResponse.CREATED(new PatternResponse(patternService.create(brandId, patternRequest)));
    }

    @GetMapping("/brands/{brandId}/patterns/{patternId}")
    public ApiResponse<PatternResponse> findPatternById(@PathVariable Long brandId,
                                                        @PathVariable Long patternId) {
        return ApiResponse.OK(new PatternResponse(patternService.findById(patternId)));
    }

    @PutMapping("/brands/{brandId}/patterns/{patternId}")
    public ApiResponse<PatternResponse> updatePattern(@PathVariable Long brandId,
                                                      @PathVariable Long patternId,
                                                      @RequestBody @Valid PatternRequest patternRequest) {
        return ApiResponse.OK(new PatternResponse(patternService.update(brandId, patternId, patternRequest)));
    }

    @DeleteMapping("/brands/{brandId}/patterns/{patternId}")
    public ApiResponse<Void> deletePatternById(@PathVariable Long brandId,
                                               @PathVariable Long patternId) {
        patternService.deleteById(patternId);
        return ApiResponse.NO_CONTENT();
    }

    @GetMapping("/vendors")
    public ApiResponse<List<VendorResponse>> findAllVendors() {
        return ApiResponse.OK(vendorService.findAll()
                .stream()
                .map(VendorResponse::new)
                .collect(Collectors.toList()));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/vendors")
    public ApiResponse<VendorResponse> createVendor(@RequestBody @Valid VendorRequest vendorRequest) {
        return ApiResponse.CREATED(new VendorResponse(vendorService.create(vendorRequest)));
    }

    @GetMapping("/vendors/{vendorId}")
    public ApiResponse<VendorResponse> findVendorById(@PathVariable Long vendorId) {
        return ApiResponse.OK(new VendorResponse(vendorService.findById(vendorId)));
    }

    @PutMapping("/vendors/{vendorId}")
    public ApiResponse<VendorResponse> updateVendor(@PathVariable Long vendorId,
                                                    @RequestBody @Valid VendorRequest vendorRequest) {
        return ApiResponse.OK(new VendorResponse(vendorService.update(vendorId, vendorRequest)));
    }

    @DeleteMapping("/vendors/{vendorId}")
    public ApiResponse<Void> deleteVendorById(@PathVariable Long vendorId) {
        vendorService.deleteById(vendorId);
        return ApiResponse.NO_CONTENT();
    }

    @GetMapping("/warehouses")
    public ApiResponse<List<WarehouseResponse>> findAllWarehouses() {
        return ApiResponse.OK(warehouseService.findAll()
                .stream()
                .map(WarehouseResponse::new)
                .collect(Collectors.toList()));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/warehouses")
    public ApiResponse<WarehouseResponse> createWarehouse(@RequestBody @Valid WarehouseRequest warehouseRequest) {
        return ApiResponse.CREATED(new WarehouseResponse(warehouseService.create(warehouseRequest)));
    }

    @GetMapping("/warehouses/{warehouseId}")
    public ApiResponse<WarehouseResponse> findWarehouseById(@PathVariable Long warehouseId) {
        return ApiResponse.OK(new WarehouseResponse(warehouseService.findById(warehouseId)));
    }

    @PutMapping("/warehouses/{warehouseId}")
    public ApiResponse<WarehouseResponse> updateWarehouse(@PathVariable Long warehouseId,
                                                          @RequestBody @Valid WarehouseRequest warehouseRequest) {
        return ApiResponse.OK(new WarehouseResponse(warehouseService.update(warehouseId, warehouseRequest)));
    }

    @DeleteMapping("/warehouses/{warehouseId}")
    public ApiResponse<Void> deleteWarehouseById(@PathVariable Long warehouseId) {
        warehouseService.deleteById(warehouseId);
        return ApiResponse.NO_CONTENT();
    }
}
