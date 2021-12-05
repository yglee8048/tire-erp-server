package com.minsoo.co.tireerpserver.service.stock;

import com.minsoo.co.tireerpserver.constant.SystemMessage;
import com.minsoo.co.tireerpserver.entity.stock.Stock;
import com.minsoo.co.tireerpserver.entity.tire.TireDot;
import com.minsoo.co.tireerpserver.exception.BadRequestException;
import com.minsoo.co.tireerpserver.exception.NotFoundException;
import com.minsoo.co.tireerpserver.model.stock.request.StockRequest;
import com.minsoo.co.tireerpserver.repository.stock.StockRepository;
import com.minsoo.co.tireerpserver.service.management.WarehouseService;
import com.minsoo.co.tireerpserver.service.tire.TireDotService;
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
    private final WarehouseService warehouseService;
    private final TireDotService tireDotService;

    public List<Stock> findAll() {
        return stockRepository.findAll();
    }

    public List<Stock> findAllByTireDot(Long tireDotId) {
        TireDot tireDot = tireDotService.findById(tireDotId);
        return stockRepository.findAllByTireDot(tireDot);
    }

    public Stock findById(Long stockId) {
        return stockRepository.findById(stockId).orElseThrow(() -> {
            log.error("Can not find stock by id: {}", stockId);
            return new NotFoundException(SystemMessage.NOT_FOUND);
        });
    }

    public void modifyStocks(Long tireDotId, List<StockRequest> stockRequests) {
        TireDot tireDot = tireDotService.findById(tireDotId);
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
                .map(stockRequest -> stockRepository.save(
                        Stock.of(tireDot, stockRequest.getNickname(), warehouseService.findById(stockRequest.getWarehouseId()), stockRequest.getQuantity(), stockRequest.isLock())))
                .collect(Collectors.toSet()));
    }
}
