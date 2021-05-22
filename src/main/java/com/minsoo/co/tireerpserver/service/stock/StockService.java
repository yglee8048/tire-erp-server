package com.minsoo.co.tireerpserver.service.stock;

import com.minsoo.co.tireerpserver.api.error.exceptions.BadRequestException;
import com.minsoo.co.tireerpserver.api.error.exceptions.NotFoundException;
import com.minsoo.co.tireerpserver.model.dto.stock.*;
import com.minsoo.co.tireerpserver.model.entity.entities.stock.Stock;
import com.minsoo.co.tireerpserver.model.entity.entities.management.Warehouse;
import com.minsoo.co.tireerpserver.model.entity.entities.tire.TireDot;
import com.minsoo.co.tireerpserver.repository.management.BrandRepository;
import com.minsoo.co.tireerpserver.repository.management.PatternRepository;
import com.minsoo.co.tireerpserver.repository.stock.StockRepository;
import com.minsoo.co.tireerpserver.repository.tire.TireDotRepository;
import com.minsoo.co.tireerpserver.repository.tire.TireRepository;
import com.minsoo.co.tireerpserver.repository.management.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StockService {

    private final BrandRepository brandRepository;
    private final PatternRepository patternRepository;
    private final WarehouseRepository warehouseRepository;
    private final TireRepository tireRepository;
    private final TireDotRepository tireDotRepository;
    private final StockRepository stockRepository;

    public List<Stock> findAllByTireDotId(Long tireDotId) {
        TireDot tireDot = tireDotRepository.findById(tireDotId).orElseThrow(() -> new NotFoundException("타이어 DOT", tireDotId));
        return stockRepository.findAllByTireDot(tireDot);
    }

    public Stock findByIds(Long tireDotId, Long stockId) {
        Stock stock = stockRepository.findById(stockId).orElseThrow(() -> new NotFoundException("재고", stockId));
        if (!tireDotId.equals(stock.getTireDot().getId())) {
            log.error("Tire-dot-id is unmatched. input: {}, found: {}", tireDotId, stock.getTireDot().getId());
            throw new BadRequestException("타이어 DOT ID 가 일치하지 않습니다.");
        }

        return stock;
    }

    public List<TireStockResponse> findTireStocks(String size, String brandName, String patternName, String productId) {
        return stockRepository.findTireStocks(size, brandName, patternName, productId);
    }

    public TireStockParams findTireStockParams() {
        return new TireStockParams(
                tireRepository.findAllSizes(),
                brandRepository.findAllNames(),
                patternRepository.findAllNames(),
                tireRepository.findAllProductIds());
    }

    @Transactional
    public void modifyStocks(Long tireDotId, List<ModifyStockRequest> modifyStockRequests) {
        // validation: 재고의 합이 같아야 한다.
        TireDot tireDot = tireDotRepository.findById(tireDotId).orElseThrow(() -> new NotFoundException("타이어 DOT", tireDotId));
        if (tireDot.validateSumOfQuantity(modifyStockRequests)) {
            throw new BadRequestException("재고의 총 합이 일치하지 않습니다.");
        }

        Map<Long, ModifyStockRequest> modifyStockRequestMap = new HashMap<>();
        Set<Stock> created = new HashSet<>();
        modifyStockRequests.forEach(modifyStockRequest -> {
            if (modifyStockRequest.getStockId() == null) {
                created.add(createStock(tireDot, modifyStockRequest));
            } else {
                modifyStockRequestMap.put(modifyStockRequest.getStockId(), modifyStockRequest);
            }
        });

        Set<Stock> removed = new HashSet<>();
        tireDot.getStocks()
                .forEach(stock -> {
                    if (modifyStockRequestMap.containsKey(stock.getId())) {
                        updateStock(stock, tireDot, modifyStockRequestMap.get(stock.getId()));
                    } else {
                        removed.add(stock);
                    }
                });

        tireDot.getStocks().addAll(created);
        tireDot.getStocks().removeAll(removed);
    }

    private Stock createStock(TireDot tireDot, ModifyStockRequest modifyStockRequest) {
        Warehouse warehouse = warehouseRepository.findById(modifyStockRequest.getWarehouseId()).orElseThrow(() -> new NotFoundException("창고", modifyStockRequest.getWarehouseId()));
        return Stock.of(tireDot, warehouse, modifyStockRequest);
    }

    private void updateStock(Stock stock, TireDot tireDot, ModifyStockRequest modifyStockRequest) {
        Warehouse warehouse = warehouseRepository.findById(modifyStockRequest.getWarehouseId()).orElseThrow(() -> new NotFoundException("창고", modifyStockRequest.getWarehouseId()));

        stock.update(tireDot, warehouse, modifyStockRequest);
    }
}
