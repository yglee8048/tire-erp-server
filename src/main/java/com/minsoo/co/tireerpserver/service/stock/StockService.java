package com.minsoo.co.tireerpserver.service.stock;

import com.minsoo.co.tireerpserver.api.error.exceptions.NotFoundException;
import com.minsoo.co.tireerpserver.model.dto.stock.*;
import com.minsoo.co.tireerpserver.model.entity.entities.stock.Stock;
import com.minsoo.co.tireerpserver.model.entity.entities.management.Warehouse;
import com.minsoo.co.tireerpserver.model.entity.entities.tire.TireDot;
import com.minsoo.co.tireerpserver.repository.management.BrandRepository;
import com.minsoo.co.tireerpserver.repository.stock.StockRepository;
import com.minsoo.co.tireerpserver.repository.tire.TireDotRepository;
import com.minsoo.co.tireerpserver.repository.tire.TireRepository;
import com.minsoo.co.tireerpserver.repository.management.WarehouseRepository;
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

    private final BrandRepository brandRepository;
    private final WarehouseRepository warehouseRepository;
    private final TireRepository tireRepository;
    private final TireDotRepository tireDotRepository;
    private final StockRepository stockRepository;

    public List<Stock> findAll() {
        return stockRepository.findAllFetchAll();
    }

    public Stock findById(Long id) {
        return stockRepository.findOneFetchAllById(id).orElseThrow(() -> new NotFoundException("재고", id));
    }

    public List<TireStockResponse> findTireStocks(String size, String brandName, String pattern, String productId) {
        return stockRepository.findTireStocksByParams(size, brandName, pattern, productId);
    }

    public TireStockParams findTireStockParams() {
        return new TireStockParams(
                tireRepository.findAllSizes(),
                brandRepository.findAllBrandNames(),
                tireRepository.findAllPatterns(),
                tireRepository.findAllProductIds());
    }

    public TireStockResponse findTireStockByTireId(Long tireId) {
        return stockRepository.findTireStocksByTireId(tireId).orElseThrow(NotFoundException::new);
    }

    public List<Stock> findAllByTireId(Long tireId) {
        return stockRepository.findAllFetchWarehouseAndTireDotByTireId(tireId);
    }

    @Transactional
    public Stock createStock(ModifyStockRequest modifyStockRequest) {
        TireDot tireDot = tireDotRepository.findById(modifyStockRequest.getTireDotId()).orElseThrow(() -> new NotFoundException("타이어 DOT", modifyStockRequest.getTireDotId()));
        Warehouse warehouse = warehouseRepository.findById(modifyStockRequest.getWarehouseId()).orElseThrow(() -> new NotFoundException("창고", modifyStockRequest.getWarehouseId()));
        return stockRepository.save(Stock.of(tireDot, modifyStockRequest.getNickname(), warehouse, modifyStockRequest.getQuantity(), modifyStockRequest.isLock()));
    }

    @Transactional
    public Stock updateStock(Long stockId, ModifyStockRequest modifyStockRequest) {
        Stock stock = stockRepository.findById(modifyStockRequest.getStockId()).orElseThrow(() -> new NotFoundException("재고", modifyStockRequest.getStockId()));
        TireDot tireDot = tireDotRepository.findById(modifyStockRequest.getTireDotId()).orElseThrow(() -> new NotFoundException("타이어 DOT", modifyStockRequest.getTireDotId()));
        Warehouse warehouse = warehouseRepository.findById(modifyStockRequest.getWarehouseId()).orElseThrow(() -> new NotFoundException("창고", modifyStockRequest.getWarehouseId()));
        return stock.update(tireDot, modifyStockRequest.getNickname(), warehouse, modifyStockRequest.getQuantity(), modifyStockRequest.isLock());
    }
}
