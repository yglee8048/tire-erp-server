package com.minsoo.co.tireerpserver.stock.service;

import com.minsoo.co.tireerpserver.shared.error.exceptions.BadRequestException;
import com.minsoo.co.tireerpserver.shared.error.exceptions.NotFoundException;
import com.minsoo.co.tireerpserver.stock.model.ModifyStock;
import com.minsoo.co.tireerpserver.stock.model.StockRequest;
import com.minsoo.co.tireerpserver.stock.model.TireStockParams;
import com.minsoo.co.tireerpserver.stock.model.TireStockResponse;
import com.minsoo.co.tireerpserver.management.entity.Warehouse;
import com.minsoo.co.tireerpserver.management.repository.BrandRepository;
import com.minsoo.co.tireerpserver.management.repository.WarehouseRepository;
import com.minsoo.co.tireerpserver.stock.entity.Stock;
import com.minsoo.co.tireerpserver.stock.repository.StockRepository;
import com.minsoo.co.tireerpserver.tire.entity.Tire;
import com.minsoo.co.tireerpserver.tire.entity.TireDot;
import com.minsoo.co.tireerpserver.tire.repository.TireDotRepository;
import com.minsoo.co.tireerpserver.tire.repository.TireRepository;
import com.minsoo.co.tireerpserver.management.repository.PatternRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

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

    public List<Stock> findByTireDotId(Long tireDotId) {
        TireDot tireDot = tireDotRepository.findById(tireDotId).orElseThrow(() -> new NotFoundException("타이어 DOT", tireDotId));
        return stockRepository.findAllByTireDot(tireDot);
    }

    public List<Stock> findByTireId(Long tireId) {
        Tire tire = tireRepository.findById(tireId).orElseThrow(() -> new NotFoundException("타이어", tireId));
        return stockRepository.findAllByTireDot_IdIn(tireDotRepository.findAllIdsByTire(tire));
    }

    public Stock findById(Long stockId) {
        return stockRepository.findById(stockId).orElseThrow(() -> new NotFoundException("재고", stockId));
    }

    public List<TireStockResponse> findTireStocks(String size, String brandName, String patternName, String productId) {
        return stockRepository.findTireStocks(size, brandName, patternName, productId);
    }

    public TireStockParams findTireStockParams() {
        return new TireStockParams(
                tireRepository.findSizes(),
                brandRepository.findAllNames(),
                patternRepository.findAllNames(),
                tireRepository.findTireIdentifications());
    }

    @Transactional
    public TireDot modifyStocks(Long tireDotId, List<StockRequest> stockRequests) {
        // validation: 재고의 합이 같아야 한다.
        TireDot tireDot = tireDotRepository.findById(tireDotId).orElseThrow(() -> new NotFoundException("타이어 DOT", tireDotId));
        if (!tireDot.isValidAdjustQuantity(stockRequests)) {
            throw new BadRequestException("재고의 총 합이 일치하지 않습니다.");
        }

        tireDot.modifyStocks(stockRequests.stream()
                .map(modifyStockRequest -> {
                    Warehouse warehouse = warehouseRepository.findById(modifyStockRequest.getWarehouseId())
                            .orElseThrow(() -> new NotFoundException("창고", modifyStockRequest.getWarehouseId()));
                    return ModifyStock.of(warehouse, modifyStockRequest);
                })
                .collect(Collectors.toList()));

        return tireDot;
    }
}
