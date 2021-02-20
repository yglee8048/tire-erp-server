package com.minsoo.co.tireerpserver.service;

import com.minsoo.co.tireerpserver.api.error.errors.NotFoundException;
import com.minsoo.co.tireerpserver.model.dto.stock.*;
import com.minsoo.co.tireerpserver.model.entity.Stock;
import com.minsoo.co.tireerpserver.model.entity.TireDot;
import com.minsoo.co.tireerpserver.model.entity.Warehouse;
import com.minsoo.co.tireerpserver.repository.BrandRepository;
import com.minsoo.co.tireerpserver.repository.StockRepository;
import com.minsoo.co.tireerpserver.repository.TireRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StockService {

    private final BrandRepository brandRepository;
    private final TireRepository tireRepository;
    private final StockRepository stockRepository;

    public List<StockResponse> findAll() {
        return stockRepository.findAll()
                .stream()
                .map(StockResponse::of)
                .collect(Collectors.toList());
    }

    public StockResponse findById(Long id) {
        return StockResponse.of(stockRepository.findById(id).orElseThrow(NotFoundException::new));
    }

    public List<TireStockResponse> findTireStocks(String size, String brandName, String pattern, String productId) {
        return stockRepository.findTireStocks(size, brandName, pattern, productId);
    }

    public TireStockParams findTireStockParams() {
        return new TireStockParams(
                tireRepository.findAllSizes(),
                brandRepository.findAllBrandNames(),
                tireRepository.findAllPatterns(),
                tireRepository.findAllProductIds());
    }

    public List<StockSimpleResponse> findAllByTireId(Long tireId) {
        return stockRepository.findAllFetchByTireId(tireId)
                .stream()
                .map(StockSimpleResponse::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public StockSimpleResponse updateStockLock(Long stockId, boolean lock) {
        Stock stock = stockRepository.findFetchDotAndWarehouseById(stockId).orElseThrow(NotFoundException::new);
        stock.updateLock(lock);
        return StockSimpleResponse.of(stock);
    }

    @Transactional
    public List<StockSimpleResponse> moveStock(Long stockId, MoveStockRequest moveStockRequest) {
        Stock fromStock = stockRepository.findFetchById(stockId).orElseThrow(NotFoundException::new);
        fromStock.reduceQuantity(moveStockRequest.getQuantity());

        Stock toStock = stockRepository.findOneFetchByTireDotIdAndWarehouseId(fromStock.getTireDot().getId(), moveStockRequest.getToWarehouseId()).orElseThrow(NotFoundException::new);
        toStock.addQuantity(moveStockRequest.getQuantity());

        return Arrays.asList(StockSimpleResponse.of(fromStock), StockSimpleResponse.of(toStock));
    }
}
