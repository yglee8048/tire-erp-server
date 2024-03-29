package com.minsoo.co.tireerpserver.api;

import com.minsoo.co.tireerpserver.constant.SaleSource;
import com.minsoo.co.tireerpserver.constant.SaleStatus;
import com.minsoo.co.tireerpserver.model.ApiResponse;
import com.minsoo.co.tireerpserver.model.request.sale.DeliveryRequest;
import com.minsoo.co.tireerpserver.model.request.sale.SaleCreateRequest;
import com.minsoo.co.tireerpserver.model.request.sale.SaleDateType;
import com.minsoo.co.tireerpserver.model.request.sale.SaleMemoRequest;
import com.minsoo.co.tireerpserver.model.request.sale.SaleUpdateRequest;
import com.minsoo.co.tireerpserver.model.response.sale.SaleContentGridResponse;
import com.minsoo.co.tireerpserver.model.response.sale.DeliveryResponse;
import com.minsoo.co.tireerpserver.model.response.sale.SaleMemoResponse;
import com.minsoo.co.tireerpserver.model.response.sale.SaleResponse;
import com.minsoo.co.tireerpserver.service.sale.DeliveryService;
import com.minsoo.co.tireerpserver.service.sale.SaleContentService;
import com.minsoo.co.tireerpserver.service.sale.SaleMemoService;
import com.minsoo.co.tireerpserver.service.sale.SaleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SaleApi {

    private final SaleService saleService;
    private final SaleContentService saleContentService;
    private final SaleMemoService saleMemoService;
    private final DeliveryService deliveryService;

    @GetMapping("/sale-content-grids")
    public ApiResponse<List<SaleContentGridResponse>> findAllSaleContents(@RequestParam(required = false) SaleStatus status,
                                                                          @RequestParam(required = false) SaleSource source,
                                                                          @RequestParam(required = false) SaleDateType saleDateType,
                                                                          @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
                                                                          @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to) {
        return ApiResponse.OK(saleContentService.findAllSaleContentGrids(status, source, saleDateType, from, to));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/sales")
    public ApiResponse<SaleResponse> createSale(@RequestBody @Valid SaleCreateRequest saleCreateRequest) {
        return ApiResponse.CREATED(saleService.create(saleCreateRequest, SaleSource.MANUAL));
    }

    @GetMapping("/sales/{saleId}")
    public ApiResponse<SaleResponse> findSaleById(@PathVariable Long saleId) {
        return ApiResponse.OK(saleService.findById(saleId));
    }

    @PutMapping("/sales/{saleId}")
    public ApiResponse<SaleResponse> updateSale(@PathVariable Long saleId,
                                                @RequestBody @Valid SaleUpdateRequest saleUpdateRequest) {
        return ApiResponse.OK(saleService.update(saleId, saleUpdateRequest, SaleSource.MANUAL));
    }

    @DeleteMapping("/sales/{saleId}")
    public ApiResponse<Void> deleteSaleById(@PathVariable Long saleId) {
        saleService.deleteById(saleId);
        return ApiResponse.NO_CONTENT();
    }

    @PostMapping("/sales/{saleId}/confirm")
    public ApiResponse<SaleResponse> confirmSale(@PathVariable Long saleId) {
        return ApiResponse.OK(saleService.confirm(saleId));
    }

    @PostMapping("/sales/{saleId}/complete")
    public ApiResponse<SaleResponse> completeSale(@PathVariable Long saleId) {
        return ApiResponse.OK(saleService.complete(saleId));
    }

    @GetMapping("/sales/{saleId}/sale-content-grids")
    public ApiResponse<List<SaleContentGridResponse>> findSaleContentsBySaleId(@PathVariable Long saleId) {
        return ApiResponse.OK(saleContentService.findSaleContentGridsBySaleId(saleId));
    }

    @GetMapping("/sales/{saleId}/delivery")
    public ApiResponse<DeliveryResponse> findDeliveryBySaleId(@PathVariable Long saleId) {
        return ApiResponse.OK(deliveryService.findBySaleId(saleId));
    }

    @PutMapping("/sales/{saleId}/delivery")
    public ApiResponse<DeliveryResponse> updateDelivery(@PathVariable Long saleId,
                                                        @RequestBody @Valid DeliveryRequest deliveryRequest) {
        return ApiResponse.OK(deliveryService.update(saleId, deliveryRequest));
    }

    @GetMapping("/sales/{saleId}/sale-memos")
    public ApiResponse<List<SaleMemoResponse>> findAllSaleMemosBySaleId(@PathVariable Long saleId) {
        return ApiResponse.OK(saleMemoService.findAllBySale(saleId));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/sales/{saleId}/sale-memos")
    public ApiResponse<SaleMemoResponse> createSaleMemo(@PathVariable Long saleId,
                                                        @RequestBody @Valid SaleMemoRequest saleMemoRequest) {
        return ApiResponse.CREATED(saleMemoService.create(saleId, saleMemoRequest));
    }

    @PutMapping("/sales/{saleId}/sale-memos/{saleMemoId}")
    public ApiResponse<SaleMemoResponse> updateSaleMemo(@PathVariable Long saleId,
                                                        @PathVariable Long saleMemoId,
                                                        @RequestBody @Valid SaleMemoRequest saleMemoRequest) {
        return ApiResponse.OK(saleMemoService.update(saleMemoId, saleMemoRequest));
    }

    @DeleteMapping("/sales/{saleId}/sale-memos/{saleMemoId}")
    public ApiResponse<Void> deleteSaleMemoById(@PathVariable Long saleId,
                                                @PathVariable Long saleMemoId) {
        saleMemoService.deleteById(saleMemoId);
        return ApiResponse.NO_CONTENT();
    }
}
