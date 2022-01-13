package com.minsoo.co.tireerpserver.api;

import com.minsoo.co.tireerpserver.model.ApiResponse;
import com.minsoo.co.tireerpserver.model.request.stock.StockMoveRequest;
import com.minsoo.co.tireerpserver.model.response.stock.StockGridResponse;
import com.minsoo.co.tireerpserver.service.stock.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class StockApi {

    private final StockService stockService;

    @GetMapping("/tire-dots/{tireDotId}/stock-grids")
    public ApiResponse<List<StockGridResponse>> findStockGridsByTireDotId(@PathVariable Long tireDotId) {
        return ApiResponse.OK(stockService.findStockGridsByTireDotId(tireDotId));
    }

    @PutMapping("/tire-dots/{tireDotId}/stocks")
    public ApiResponse<Void> updateStocks(@PathVariable Long tireDotId,
                                          @RequestBody @Valid List<StockMoveRequest> stockMoveRequests) {
        stockService.modifyStocks(tireDotId, stockMoveRequests);
        return ApiResponse.NO_CONTENT();
    }
}
