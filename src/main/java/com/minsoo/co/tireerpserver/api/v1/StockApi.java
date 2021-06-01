package com.minsoo.co.tireerpserver.api.v1;

import com.minsoo.co.tireerpserver.model.dto.stock.*;
import com.minsoo.co.tireerpserver.model.response.ApiResponse;
import com.minsoo.co.tireerpserver.service.stock.StockService;
import io.swagger.v3.oas.annotations.Operation;
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
public class StockApi {

    private final StockService stockService;

    // STOCKS
    @GetMapping("/tire-dots/{tireDotId}/stocks")
    @Operation(summary = "재고 목록 조회")
    public ApiResponse<List<StockResponse>> findAllStocksByTireDotId(@PathVariable Long tireDotId) {
        return ApiResponse.OK(stockService.findAllByTireDotId(tireDotId)
                .stream()
                .map(StockResponse::of)
                .collect(Collectors.toList()));
    }

    @GetMapping("/tire-dots/{tireDotId}/stocks/{stockId}")
    @Operation(summary = "재고 상세 조회")
    public ApiResponse<StockResponse> findStockByIds(@PathVariable Long tireDotId,
                                                     @PathVariable Long stockId) {
        return ApiResponse.OK(StockResponse.of(stockService.findById(stockId)));
    }

    @PutMapping("/tire-dots/{tireDotId}/stocks")
    @Operation(summary = "재고 분배", description = "tire-dot 하위의 재고를 별칭과 창고 등으로 나눠 적절히 분배한다.")
    public ApiResponse<Object> modifyStocks(@PathVariable(name = "tireDotId") Long tireDotId,
                                            @RequestBody @Valid List<StockRequest> stockRequests) {
        stockService.modifyStocks(tireDotId, stockRequests);
        return ApiResponse.OK;
    }

    // TIRE-STOCKS
    @GetMapping("/tire-stocks")
    @Operation(summary = "타이어-재고 목록 조회")
    public ApiResponse<List<TireStockResponse>> findTireStocks(@RequestParam(required = false) String size,
                                                               @RequestParam(required = false) String brandName,
                                                               @RequestParam(required = false) String patternName,
                                                               @RequestParam(required = false) String productId) {
        return ApiResponse.OK(stockService.findTireStocks(size, brandName, patternName, productId));
    }

    @GetMapping("/tire-stocks/params")
    @Operation(summary = "타이어-재고 검색 조건 조회")
    public ApiResponse<TireStockParams> findTireStockParams() {
        return ApiResponse.OK(stockService.findTireStockParams());
    }
}