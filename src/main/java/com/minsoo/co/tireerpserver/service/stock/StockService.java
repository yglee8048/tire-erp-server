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
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
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
        
        validateStockQuantity(tireDot, stockMoveRequests);
        validateNicknameDuplication(stockMoveRequests);

        List<Long> removable = stockRepository.findAllByTireDot(tireDot).stream()
                .map(Stock::getId)
                .collect(Collectors.toList());
        List<Long> stored = new ArrayList<>();

        for (StockMoveRequest stockMoveRequest : stockMoveRequests) {
            Warehouse warehouse = findWarehouseById(stockMoveRequest);
            stored.add(stockRepository.findByTireDotAndWarehouseAndNickname(tireDot, warehouse, stockMoveRequest.getNickname())
                    .map(found -> found.update(warehouse, stockMoveRequest.getQuantity(), stockMoveRequest.isLock()))
                    .orElseGet(() -> stockRepository.save(Stock.of(tireDot, stockMoveRequest.getNickname(), warehouse, stockMoveRequest.getQuantity(), stockMoveRequest.isLock())))
                    .getId());
        }

        removable.removeAll(stored);
        if (!CollectionUtils.isEmpty(removable)) {
            stockRepository.deleteAllByIdIn(removable);
        }
    }

    private void validateStockQuantity(TireDot tireDot, List<StockMoveRequest> stockMoveRequests) {
        if (!tireDot.isValidStockRequests(stockMoveRequests)) {
            throw new BadRequestException(SystemMessage.DISCREPANCY_STOCK_QUANTITY);
        }
    }

    private void validateNicknameDuplication(List<StockMoveRequest> stockMoveRequests) {
        Set<String> nicknames = stockMoveRequests.stream()
                .map(StockMoveRequest::getNickname)
                .collect(Collectors.toSet());
        if (nicknames.size() != stockMoveRequests.size()) {
            throw new BadRequestException(SystemMessage.NICKNAME_DUPLICATE);
        }
    }

    private Warehouse findWarehouseById(StockMoveRequest stockMoveRequest) {
        return warehouseRepository.findById(stockMoveRequest.getWarehouseId()).orElseThrow(() -> {
            log.error("Can not find warehouse by id: {}", stockMoveRequest.getWarehouseId());
            return new NotFoundException(SystemMessage.NOT_FOUND + ": [창고]");
        });
    }

    private TireDot findTireDotById(Long tireDotId) {
        return tireDotRepository.findById(tireDotId).orElseThrow(() -> {
            log.error("Can not find tire dot by id: {}", tireDotId);
            return new NotFoundException(SystemMessage.NOT_FOUND + ": [타이어 DOT]");
        });
    }
}
