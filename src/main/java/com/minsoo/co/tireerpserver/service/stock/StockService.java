package com.minsoo.co.tireerpserver.service.stock;

import com.minsoo.co.tireerpserver.constant.SystemMessage;
import com.minsoo.co.tireerpserver.entity.management.Warehouse;
import com.minsoo.co.tireerpserver.entity.stock.Stock;
import com.minsoo.co.tireerpserver.entity.tire.TireDot;
import com.minsoo.co.tireerpserver.exception.BadRequestException;
import com.minsoo.co.tireerpserver.exception.NotFoundException;
import com.minsoo.co.tireerpserver.model.request.stock.StockRequest;
import com.minsoo.co.tireerpserver.repository.management.WarehouseRepository;
import com.minsoo.co.tireerpserver.repository.stock.StockRepository;
import com.minsoo.co.tireerpserver.repository.tire.TireDotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;
    private final WarehouseRepository warehouseRepository;
    private final TireDotRepository tireDotRepository;

    public List<Stock> findAll() {
        return stockRepository.findAll();
    }

    public List<Stock> findAllByTireDot(Long tireDotId) {
        TireDot tireDot = findTireDotById(tireDotId);
        return stockRepository.findAllByTireDot(tireDot);
    }

    public Stock findById(Long stockId) {
        return stockRepository.findById(stockId).orElseThrow(() -> {
            log.error("Can not find stock by id: {}", stockId);
            return new NotFoundException(SystemMessage.NOT_FOUND);
        });
    }

    public void modifyStocks(Long tireDotId, List<StockRequest> stockRequests) {
        TireDot tireDot = findTireDotById(tireDotId);
        if (!tireDot.isValidStockRequests(stockRequests)) {
            throw new BadRequestException(SystemMessage.DISCREPANCY_STOCK_QUANTITY);
        }

        Set<String> nicknames = stockRequests.stream()
                .map(StockRequest::getNickname)
                .collect(Collectors.toSet());
        if (nicknames.size() != stockRequests.size()) {
            throw new BadRequestException(SystemMessage.NICKNAME_DUPLICATE);
        }

        tireDot.getStocks().clear();
        tireDot.getStocks().addAll(stockRequests.stream()
                .map(stockRequest -> {
                    Warehouse warehouse = warehouseRepository.findById(stockRequest.getWarehouseId()).orElseThrow(() -> {
                        log.error("Can not find warehouse by id: {}", stockRequest.getWarehouseId());
                        return new NotFoundException(SystemMessage.NOT_FOUND + ": [창고]");
                    });
                    return stockRepository.save(
                            Stock.of(tireDot, stockRequest.getNickname(), warehouse, stockRequest.getQuantity(), stockRequest.isLock()));
                })
                .collect(Collectors.toSet()));
    }

    private TireDot findTireDotById(Long tireDotId) {
        return tireDotRepository.findById(tireDotId).orElseThrow(() -> {
            log.error("Can not find tire dot by id: {}", tireDotId);
            return new NotFoundException(SystemMessage.NOT_FOUND + ": [타이어 DOT]");
        });
    }
}
