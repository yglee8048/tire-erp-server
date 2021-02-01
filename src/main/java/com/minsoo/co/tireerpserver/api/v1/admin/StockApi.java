package com.minsoo.co.tireerpserver.api.v1.admin;

import com.minsoo.co.tireerpserver.model.dto.ResponseDTO;
import com.minsoo.co.tireerpserver.model.dto.stock.StockResponse;
import com.minsoo.co.tireerpserver.model.dto.stock.TireStockResponse;
import com.minsoo.co.tireerpserver.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/admin/api/v1/stocks")
@RequiredArgsConstructor
public class StockApi {

    private final StockService stockService;

    @GetMapping(value = "/tires")
    private ResponseDTO<List<TireStockResponse>> findTireStocks(@RequestParam(value = "size", required = false) String size,
                                                                @RequestParam(value = "brand_name", required = false) String brandName,
                                                                @RequestParam(value = "pattern", required = false) String pattern,
                                                                @RequestParam(value = "product_id", required = false) String productId) {
        return new ResponseDTO<>(stockService.findTireStocks(size, brandName, pattern, productId));
    }

    @GetMapping(value = "/tires/{tireId}/dots")
    private ResponseDTO<List<StockResponse>> findTireDotStocks(@PathVariable(value = "tireId") Long tireId) {
        return new ResponseDTO<>(stockService.findAllByTireId(tireId)
                .stream()
                .map(StockResponse::new)
                .collect(Collectors.toList()));
    }

    @PatchMapping(value = "/{stockId}/lock")
    private ResponseDTO<StockResponse> patchStockLock(@PathVariable(value = "stockId") Long stockId,
                                                      @RequestParam(value = "lock") boolean lock) {
        return new ResponseDTO<>(new StockResponse(stockService.updateStockLock(stockId, lock)));
    }
}
