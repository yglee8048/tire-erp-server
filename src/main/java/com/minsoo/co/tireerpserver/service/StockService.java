package com.minsoo.co.tireerpserver.service;

import com.minsoo.co.tireerpserver.api.error.errors.NotFoundException;
import com.minsoo.co.tireerpserver.model.dto.stock.*;
import com.minsoo.co.tireerpserver.model.entity.Stock;
import com.minsoo.co.tireerpserver.model.entity.Warehouse;
import com.minsoo.co.tireerpserver.repository.BrandRepository;
import com.minsoo.co.tireerpserver.repository.StockRepository;
import com.minsoo.co.tireerpserver.repository.TireRepository;
import com.minsoo.co.tireerpserver.repository.WarehouseRepository;
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
    private final WarehouseRepository warehouseRepository;
    private final TireRepository tireRepository;
    private final StockRepository stockRepository;

    public List<StockResponse> findAll() {
        return stockRepository.findAllFetchAll()
                .stream()
                .map(StockResponse::of)
                .collect(Collectors.toList());
    }

    public StockResponse findById(Long id) {
        return StockResponse.of(stockRepository.findOneFetchAllById(id).orElseThrow(NotFoundException::new));
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
        return stockRepository.findAllFetchWarehouseAndTireDotByTireId(tireId)
                .stream()
                .map(StockSimpleResponse::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public StockSimpleResponse updateStockLock(Long stockId, StockUpdateLockRequest updateLockRequest) {
        Stock stock = stockRepository.findOneFetchWarehouseAndTireDotById(stockId).orElseThrow(NotFoundException::new);
        stock.updateLock(updateLockRequest.isLock());
        return StockSimpleResponse.of(stock);
    }

    @Transactional
    public List<StockSimpleResponse> moveStock(Long stockId, MoveStockRequest moveStockRequest) {
        Stock fromStock = stockRepository.findOneFetchWarehouseAndTireDotById(stockId).orElseThrow(NotFoundException::new);
        fromStock.reduceQuantity(moveStockRequest.getQuantity());

        Warehouse toWarehouse = warehouseRepository.findById(moveStockRequest.getToWarehouseId()).orElseThrow(NotFoundException::new);
        Stock toStock = stockRepository.findOneFetchWarehouseAndTireDotByWarehouseAndTireDot(toWarehouse, fromStock.getTireDot())
                // 대상 재고 객체가 존재하지 않으면, 생성하여 적용한다.
                .orElseGet(() -> stockRepository.save(Stock.of(fromStock.getTireDot(), toWarehouse, fromStock.isLock())));

        toStock.addQuantity(moveStockRequest.getQuantity());
        return Arrays.asList(StockSimpleResponse.of(fromStock), StockSimpleResponse.of(toStock));
    }
}
