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
    public ApiResponse<List<StockResponse>> findAllStocksByIds(@PathVariable(name = "tireDotId") Long tireDotId) {
        return ApiResponse.OK(stockService.findAllByTireDotId(tireDotId)
                .stream()
                .map(StockResponse::of)
                .collect(Collectors.toList()));
    }

    @GetMapping("/tire-dots/{tireDotId}/stocks/{stockId}")
    public ApiResponse<StockResponse> findStockByIds(@PathVariable(name = "tireDotId") Long tireDotId,
                                                     @PathVariable(name = "stockId") Long stockId) {
        return ApiResponse.OK(StockResponse.of(stockService.findByIds(tireDotId, stockId)));
    }

    @PostMapping("/tire-dots/{tireDotId}/stocks")
    public ApiResponse<String> modifyStocks(@PathVariable(name = "tireDotId") Long tireDotId,
                                            @RequestBody @Valid List<ModifyStockRequest> modifyStockRequests) {
        stockService.modifyStocks(tireDotId, modifyStockRequests);
        return ApiResponse.OK;
    }

    // TIRE-STOCKS
    @GetMapping("/tire-stocks")
    public ApiResponse<List<TireStockResponse>> findTireStocks() {
        return null;
    }
}