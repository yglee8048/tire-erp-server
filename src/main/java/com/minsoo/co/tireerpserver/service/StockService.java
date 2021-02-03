package com.minsoo.co.tireerpserver.service;

import com.minsoo.co.tireerpserver.api.error.errors.NotFoundException;
import com.minsoo.co.tireerpserver.model.dto.stock.MoveStockRequest;
import com.minsoo.co.tireerpserver.model.dto.stock.TireStockParamResponse;
import com.minsoo.co.tireerpserver.model.dto.stock.TireStockResponse;
import com.minsoo.co.tireerpserver.model.entity.Stock;
import com.minsoo.co.tireerpserver.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;

    public List<TireStockResponse> findTireStocks(String size, String brandName, String pattern, String productId) {
        return stockRepository.findTireStocks(size, brandName, pattern, productId);
    }

    public TireStockParamResponse findTireStockParams() {
        TireStockParamResponse paramResponse = new TireStockParamResponse();
        stockRepository.findTireStockParams()
                .forEach(paramResponse::addParams);
        return paramResponse;
    }

    public List<Stock> findAllByTireId(Long tireId) {
        return stockRepository.findAllFetchByTireId(tireId);
    }

    @Transactional
    public Stock updateStockLock(Long stockId, boolean lock) {
        Stock stock = stockRepository.findFetchDotAndWarehouseById(stockId).orElseThrow(NotFoundException::new);
        stock.updateLock(lock);
        return stock;
    }

    @Transactional
    public List<Stock> moveStock(MoveStockRequest moveStockRequest) {
        Stock fromStock = stockRepository.findFetchById(moveStockRequest.getFromStockId()).orElseThrow(NotFoundException::new);
        fromStock.reduceQuantity(moveStockRequest.getQuantity());

        Stock toStock = stockRepository.findOneFetchByTireDotIdAndWarehouseName(fromStock.getTireDot().getId(), moveStockRequest.getToWarehouseName()).orElseThrow(NotFoundException::new);
        toStock.addQuantity(moveStockRequest.getQuantity());

        return Arrays.asList(fromStock, toStock);
    }
}
