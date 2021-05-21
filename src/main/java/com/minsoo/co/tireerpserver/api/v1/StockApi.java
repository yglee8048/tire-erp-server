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
@RequestMapping(value = "/api/v1/stocks")
@RequiredArgsConstructor
public class StockApi {

    private final StockService stockService;

    @GetMapping
    public ApiResponse<List<StockResponse>> findAllStocks() {
        return ApiResponse.OK(stockService.findAll()
                .stream()
                .map(StockResponse::of)
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/{stockId}")
    public ApiResponse<StockResponse> findStockById(@PathVariable(value = "stockId") Long stockId) {
        return ApiResponse.OK(StockResponse.of(stockService.findById(stockId)));
    }

    @PostMapping(value = "/{stockId}/move-stock")
    public ApiResponse<String> moveStock(@PathVariable(value = "stockId") Long stockId,
                                         @RequestBody @Valid MoveStockRequest moveStockRequest) {
        stockService.moveStock(stockId, moveStockRequest);
        return ApiResponse.OK;
    }

    // TIRE-STOCKS
    @GetMapping(value = "/tires")
    public ApiResponse<List<TireStockResponse>> findTireStocks(@RequestParam(value = "size", required = false) String size,
                                                               @RequestParam(value = "brand_name", required = false) String brandName,
                                                               @RequestParam(value = "pattern", required = false) String pattern,
                                                               @RequestParam(value = "product_id", required = false) String productId) {
        return ApiResponse.OK(stockService.findTireStocks(size, brandName, pattern, productId));
    }

    @GetMapping(value = "/tires/params")
    public ApiResponse<TireStockParams> findTireStockParams() {
        return ApiResponse.OK(stockService.findTireStockParams());
    }

    @GetMapping(value = "/tires/{tireId}")
    public ApiResponse<TireStockResponse> findTireStockByTireId(@PathVariable(value = "tireId") Long tireId) {
        return ApiResponse.OK(stockService.findTireStockByTireId(tireId));
    }

    @GetMapping(value = "/tires/{tireId}/dots")
    public ApiResponse<List<StockSimpleResponse>> findTireDotStocks(@PathVariable(value = "tireId") Long tireId) {
        return ApiResponse.OK(stockService.findAllByTireId(tireId)
                .stream()
                .map(StockSimpleResponse::of)
                .collect(Collectors.toList()));
    }
}