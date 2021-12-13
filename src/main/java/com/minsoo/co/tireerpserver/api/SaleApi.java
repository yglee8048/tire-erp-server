package com.minsoo.co.tireerpserver.api;

import com.minsoo.co.tireerpserver.constant.SaleSource;
import com.minsoo.co.tireerpserver.constant.SaleStatus;
import com.minsoo.co.tireerpserver.entity.sale.Sale;
import com.minsoo.co.tireerpserver.model.ApiResponse;
import com.minsoo.co.tireerpserver.model.request.sale.SaleDateType;
import com.minsoo.co.tireerpserver.model.request.sale.SaleMemoRequest;
import com.minsoo.co.tireerpserver.model.request.sale.SaleRequest;
import com.minsoo.co.tireerpserver.model.request.stock.SaleConfirmRequest;
import com.minsoo.co.tireerpserver.model.response.grid.SaleContentGridResponse;
import com.minsoo.co.tireerpserver.model.response.sale.SaleMemoResponse;
import com.minsoo.co.tireerpserver.model.response.sale.SaleResponse;
import com.minsoo.co.tireerpserver.service.sale.SaleContentService;
import com.minsoo.co.tireerpserver.service.sale.SaleMemoService;
import com.minsoo.co.tireerpserver.service.sale.SaleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SaleApi {

    private final SaleService saleService;
    private final SaleContentService saleContentService;
    private final SaleMemoService saleMemoService;

    @GetMapping("/sale-content-grids")
    public ApiResponse<List<SaleContentGridResponse>> findAllSaleContents(@RequestParam(required = false) SaleStatus status,
                                                                          @RequestParam(required = false) SaleSource source,
                                                                          @RequestParam SaleDateType saleDateType,
                                                                          @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
                                                                          @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to) {
        return ApiResponse.OK(saleContentService.findAll().stream()
                .filter(saleContent -> {
                    Sale sale = saleContent.getSale();
                    switch (saleDateType) {
                        case RELEASE:
                            return sale.getReleaseDate().isAfter(from) && sale.getReleaseDate().isBefore(to);
                        case TRANSACTION:
                            return sale.getTransactionDate().isAfter(from) && sale.getTransactionDate().isBefore(to);
                        case DESIRED_DELIVERY:
                            return sale.getDesiredDeliveryDate().isAfter(from) && sale.getDesiredDeliveryDate().isBefore(to);
                        default:
                            throw new IllegalStateException();
                    }
                })
                .filter(saleContent -> {
                    if (status != null) {
                        return saleContent.getSale().getStatus().equals(status);
                    } else {
                        return true;
                    }
                })
                .filter(saleContent -> {
                    if (source != null) {
                        return saleContent.getSale().getSource().equals(source);
                    } else {
                        return true;
                    }
                })
                .map(SaleContentGridResponse::new)
                .collect(Collectors.toList()));
    }

    @GetMapping("/sales/{saleId}/sale-content-grids")
    public ApiResponse<List<SaleContentGridResponse>> findSaleContentsBySaleId(@PathVariable Long saleId) {
        return ApiResponse.OK(saleContentService.findAllBySaleId(saleId).stream()
                .map(SaleContentGridResponse::new)
                .collect(Collectors.toList()));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/sales")
    public ApiResponse<SaleResponse> createSale(@RequestBody @Valid SaleRequest saleRequest) {
        return ApiResponse.CREATED(new SaleResponse(saleService.create(saleRequest, SaleSource.MANUAL)));
    }

    @GetMapping("/sales/{saleId}")
    public ApiResponse<SaleResponse> findSaleById(@PathVariable Long saleId) {
        return ApiResponse.OK(new SaleResponse(saleService.findById(saleId)));
    }

    @PutMapping("/sales/{saleId}")
    public ApiResponse<SaleResponse> updateSale(@PathVariable Long saleId,
                                                @RequestBody @Valid SaleRequest saleRequest) {
        return ApiResponse.OK(new SaleResponse(saleService.update(saleId, saleRequest)));
    }

    @DeleteMapping("/sales/{saleId}")
    public ApiResponse<Void> deleteSaleById(@PathVariable Long saleId) {
        saleService.deleteById(saleId);
        return ApiResponse.NO_CONTENT();
    }

    @PostMapping("/sales/{saleId}/confirm")
    public ApiResponse<SaleResponse> confirmSale(@PathVariable Long saleId,
                                                 @RequestBody @Valid List<SaleConfirmRequest> saleConfirmRequests) {
        return ApiResponse.OK(new SaleResponse(saleService.confirm(saleId, saleConfirmRequests)));
    }

    @GetMapping("/sales/{saleId}/sale-memos")
    public ApiResponse<List<SaleMemoResponse>> findAllSaleMemosBySaleId(@PathVariable Long saleId) {
        return ApiResponse.OK(saleMemoService.findAllBySale(saleId).stream()
                .map(SaleMemoResponse::new)
                .collect(Collectors.toList()));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/sales/{saleId}/sale-memos")
    public ApiResponse<SaleMemoResponse> createSaleMemo(@PathVariable Long saleId,
                                                        @RequestBody @Valid SaleMemoRequest saleMemoRequest) {
        return ApiResponse.CREATED(new SaleMemoResponse(saleMemoService.create(saleId, saleMemoRequest)));
    }

    @PutMapping("/sales/{saleId}/sale-memos/{saleMemoId}")
    public ApiResponse<SaleMemoResponse> updateSaleMemo(@PathVariable Long saleId,
                                                        @PathVariable Long saleMemoId,
                                                        @RequestBody @Valid SaleMemoRequest saleMemoRequest) {
        return ApiResponse.OK(new SaleMemoResponse(saleMemoService.update(saleMemoId, saleMemoRequest)));
    }

    @DeleteMapping("/sales/{saleId}/sale-memos/{saleMemoId}")
    public ApiResponse<Void> deleteSaleMemoById(@PathVariable Long saleId,
                                                @PathVariable Long saleMemoId) {
        saleMemoService.deleteById(saleMemoId);
        return ApiResponse.NO_CONTENT();
    }
}
