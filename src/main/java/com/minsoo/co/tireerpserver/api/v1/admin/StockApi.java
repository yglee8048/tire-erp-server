package com.minsoo.co.tireerpserver.api.v1.admin;

import com.minsoo.co.tireerpserver.model.dto.response.ApiResponseDTO;
import com.minsoo.co.tireerpserver.model.dto.stock.MoveStockRequest;
import com.minsoo.co.tireerpserver.model.dto.stock.StockResponse;
import com.minsoo.co.tireerpserver.model.dto.stock.TireStockParamResponse;
import com.minsoo.co.tireerpserver.model.dto.stock.TireStockResponse;
import com.minsoo.co.tireerpserver.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/admin/api/v1/stocks")
@RequiredArgsConstructor
public class StockApi {

    private final StockService stockService;

    @GetMapping(value = "/tires")
    private ApiResponseDTO<List<TireStockResponse>> findTireStocks(@RequestParam(value = "size", required = false) String size,
                                                                   @RequestParam(value = "brand_name", required = false) String brandName,
                                                                   @RequestParam(value = "pattern", required = false) String pattern,
                                                                   @RequestParam(value = "product_id", required = false) String productId) {
        return ApiResponseDTO.createOK(stockService.findTireStocks(size, brandName, pattern, productId));
    }

    @GetMapping(value = "/tires/params")
    private ApiResponseDTO<TireStockParamResponse> findTireStockParams() {
        return ApiResponseDTO.createOK(stockService.findTireStockParams());
    }

    @GetMapping(value = "/tires/{tireId}/dots")
    private ApiResponseDTO<List<StockResponse>> findTireDotStocks(@PathVariable(value = "tireId") Long tireId) {
        return ApiResponseDTO.createOK(stockService.findAllByTireId(tireId)
                .stream()
                .map(StockResponse::of)
                .collect(Collectors.toList()));
    }

    @PostMapping(value = "/{stockId}/lock")
    private ApiResponseDTO<StockResponse> updateStockLock(@PathVariable(value = "stockId") Long stockId,
                                                          @RequestParam(value = "lock") boolean lock) {
        return ApiResponseDTO.createOK(StockResponse.of(stockService.updateStockLock(stockId, lock)));
    }

    @PostMapping(value = "/move")
    private ApiResponseDTO<List<StockResponse>> moveStock(@RequestBody @Valid MoveStockRequest moveStockRequest) {
        return ApiResponseDTO.createOK(stockService.moveStock(moveStockRequest)
                .stream()
                .map(StockResponse::of)
                .collect(Collectors.toList()));
    }
}
