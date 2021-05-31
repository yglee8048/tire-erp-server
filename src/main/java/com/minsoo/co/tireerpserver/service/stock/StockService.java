package com.minsoo.co.tireerpserver.service.stock;

import com.minsoo.co.tireerpserver.api.error.exceptions.BadRequestException;
import com.minsoo.co.tireerpserver.api.error.exceptions.NotFoundException;
import com.minsoo.co.tireerpserver.model.dto.stock.*;
import com.minsoo.co.tireerpserver.model.entity.stock.Stock;
import com.minsoo.co.tireerpserver.model.entity.management.Warehouse;
import com.minsoo.co.tireerpserver.model.entity.tire.TireDot;
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

    public List<Stock> findAllByTireDotId(Long tireDotId) {
        TireDot tireDot = tireDotRepository.findById(tireDotId).orElseThrow(() -> new NotFoundException("타이어 DOT", tireDotId));
        return stockRepository.findAllByTireDot(tireDot);
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
