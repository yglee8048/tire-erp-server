package com.minsoo.co.tireerpserver.api.v1;

import com.minsoo.co.tireerpserver.model.dto.stock.*;
import com.minsoo.co.tireerpserver.model.response.ApiResponse;
import com.minsoo.co.tireerpserver.service.stock.StockService;
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
    public ApiResponse<List<StockResponse>> findAllStocksByIds(@PathVariable Long tireDotId) {
        return ApiResponse.OK(stockService.findAllByTireDotId(tireDotId)
                .stream()
                .map(StockResponse::of)
                .collect(Collectors.toList()));
    }

    @GetMapping("/tire-dots/{tireDotId}/stocks/{stockId}")
    public ApiResponse<StockResponse> findStockByIds(@PathVariable Long tireDotId,
                                                     @PathVariable Long stockId) {
        return ApiResponse.OK(StockResponse.of(stockService.findByIds(tireDotId, stockId)));
    }

    @PostMapping("/tire-dots/{tireDotId}/stocks")
    public ApiResponse<Object> modifyStocks(@PathVariable(name = "tireDotId") Long tireDotId,
                                            @RequestBody @Valid List<ModifyStockRequest> modifyStockRequests) {
        stockService.modifyStocks(tireDotId, modifyStockRequests);
        return ApiResponse.OK;
    }

    // TIRE-STOCKS
    @GetMapping("/tire-stocks")
    public ApiResponse<List<TireStockResponse>> findTireStocks(@RequestParam(required = false) String size,
                                                               @RequestParam(required = false) String brandName,
                                                               @RequestParam(required = false) String patternName,
                                                               @RequestParam(required = false) String productId) {
        return ApiResponse.OK(stockService.findTireStocks(size, brandName, patternName, productId));
    }

    @GetMapping("/tire-stocks/params")
    public ApiResponse<TireStockParams> findTireStockParams() {
        return ApiResponse.OK(stockService.findTireStockParams());
    }
}