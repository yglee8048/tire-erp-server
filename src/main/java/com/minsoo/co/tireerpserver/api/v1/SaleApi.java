package com.minsoo.co.tireerpserver.api.v1;

import com.minsoo.co.tireerpserver.api.v1.model.ApiResponse;
import com.minsoo.co.tireerpserver.api.v1.model.dto.sale.SaleContentGridResponse;
import com.minsoo.co.tireerpserver.api.v1.model.dto.sale.SaleRequest;
import com.minsoo.co.tireerpserver.api.v1.model.dto.sale.SaleResponse;
import com.minsoo.co.tireerpserver.services.sale.service.SaleContentService;
import com.minsoo.co.tireerpserver.services.sale.service.SaleService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1")
@RequiredArgsConstructor
public class SaleApi {

    private final SaleService saleService;
    private final SaleContentService saleContentService;

    @GetMapping(value = "/sales")
    public ApiResponse<List<SaleResponse>> findSales() {
        return ApiResponse.OK(saleService.findAll()
                .stream()
                .map(SaleResponse::new)
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/sales/{saleId}")
    public ApiResponse<SaleResponse> findSaleById(@PathVariable Long saleId) {
        return ApiResponse.OK(new SaleResponse(saleService.findById(saleId)));
    }

    @PostMapping(value = "/sales")
    public ResponseEntity<ApiResponse<Object>> createSale(@RequestBody SaleRequest saleRequest) {
        return ApiResponse.CREATED(
                linkTo(methodOn(SaleApi.class).findSaleById(saleService.create(saleRequest).getId())).toUri());
    }

    // SALE-GRID
    @GetMapping(value = "/sale-content-grid")
    @Operation(summary = "매출 항목 전체 목록 조회", description = "매출 내역 목록을 보여줄 때 사용한다.(Aggregation)")
    public ApiResponse<List<SaleContentGridResponse>> findAllSaleContents(@RequestParam(required = false) LocalDate from,
                                                                          @RequestParam(required = false) LocalDate to) {
        return ApiResponse.OK(saleContentService.findAllByTransactionDate(from, to));
    }
}
