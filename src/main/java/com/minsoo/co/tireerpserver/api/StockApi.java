package com.minsoo.co.tireerpserver.api;

import com.minsoo.co.tireerpserver.model.ApiResponse;
import com.minsoo.co.tireerpserver.model.stock.request.StockRequest;
import com.minsoo.co.tireerpserver.model.stock.response.StockResponse;
import com.minsoo.co.tireerpserver.model.stock.response.TireStockResponse;
import com.minsoo.co.tireerpserver.service.stock.StockService;
import com.minsoo.co.tireerpserver.service.tire.TireService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class StockApi {

    private final StockService stockService;

    @GetMapping("/tire-dots/{tireDotId}/stocks")
    public ApiResponse<List<StockResponse>> findStocksByTireDotId(@PathVariable Long tireDotId) {
        return ApiResponse.OK(stockService.findAllByTireDot(tireDotId)
                .stream()
                .map(StockResponse::new)
                .collect(Collectors.toList()));
    }

    @GetMapping("/tire-dots/{tireDotId}/stocks/{stockId}")
    public ApiResponse<StockResponse> findStockById(@PathVariable Long tireDotId,
                                                    @PathVariable Long stockId) {
        return ApiResponse.OK(new StockResponse(stockService.findById(stockId)));
    }

    @PutMapping("/tire-dots/{tireDotId}/stocks")
    public ApiResponse<Void> updateStocks(@PathVariable Long tireDotId,
                                          @RequestBody @Valid List<StockRequest> stockRequests) {
        stockService.modifyStocks(tireDotId, stockRequests);
        return ApiResponse.NO_CONTENT();
    }
}
