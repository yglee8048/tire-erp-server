package com.minsoo.co.tireerpserver.service.stock;

import com.minsoo.co.tireerpserver.constant.SystemMessage;
import com.minsoo.co.tireerpserver.entity.management.Warehouse;
import com.minsoo.co.tireerpserver.entity.stock.Stock;
import com.minsoo.co.tireerpserver.entity.tire.TireDot;
import com.minsoo.co.tireerpserver.exception.BadRequestException;
import com.minsoo.co.tireerpserver.exception.NotFoundException;
import com.minsoo.co.tireerpserver.model.request.stock.StockMoveRequest;
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

    public void modifyStocks(Long tireDotId, List<StockMoveRequest> stockMoveRequests) {
        TireDot tireDot = findTireDotById(tireDotId);
        if (!tireDot.isValidStockRequests(stockMoveRequests)) {
            throw new BadRequestException(SystemMessage.DISCREPANCY_STOCK_QUANTITY);
        }

        Set<String> nicknames = stockMoveRequests.stream()
                .map(StockMoveRequest::getNickname)
                .collect(Collectors.toSet());
        if (nicknames.size() != stockMoveRequests.size()) {
            throw new BadRequestException(SystemMessage.NICKNAME_DUPLICATE);
        }

        // FIXME:
        tireDot.getStocks().clear();
        tireDot.getStocks().addAll(stockMoveRequests.stream()
                .map(stockMoveRequest -> {
                    Warehouse warehouse = warehouseRepository.findById(stockMoveRequest.getWarehouseId()).orElseThrow(() -> {
                        log.error("Can not find warehouse by id: {}", stockMoveRequest.getWarehouseId());
                        return new NotFoundException(SystemMessage.NOT_FOUND + ": [창고]");
                    });
                    return stockRepository.save(
                            Stock.of(tireDot, stockMoveRequest.getNickname(), warehouse, stockMoveRequest.getQuantity(), stockMoveRequest.isLock()));
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
