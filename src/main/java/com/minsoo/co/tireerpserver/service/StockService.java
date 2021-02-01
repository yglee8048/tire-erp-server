package com.minsoo.co.tireerpserver.service;

import com.minsoo.co.tireerpserver.api.error.errors.NotFoundException;
import com.minsoo.co.tireerpserver.model.dto.stock.TireStockResponse;
import com.minsoo.co.tireerpserver.model.entity.Stock;
import com.minsoo.co.tireerpserver.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public List<Stock> findAllByTireId(Long tireId) {
        return stockRepository.findAllByTireId(tireId);
    }

    @Transactional
    public Stock updateStockLock(Long stockId, boolean lock) {
        Stock stock = stockRepository.findFetchDotAndWarehouseById(stockId).orElseThrow(NotFoundException::new);
        stock.updateLock(lock);
        return stock;
    }
}
