package com.minsoo.co.tireerpserver.service.sale;

import com.minsoo.co.tireerpserver.constant.SaleSource;
import com.minsoo.co.tireerpserver.constant.SaleStatus;
import com.minsoo.co.tireerpserver.entity.sale.Sale;
import com.minsoo.co.tireerpserver.entity.sale.SaleContent;
import com.minsoo.co.tireerpserver.entity.stock.Stock;
import com.minsoo.co.tireerpserver.entity.tire.TireDot;
import com.minsoo.co.tireerpserver.exception.NotFoundException;
import com.minsoo.co.tireerpserver.model.request.sale.SaleContentRequest;
import com.minsoo.co.tireerpserver.model.request.sale.SaleDateType;
import com.minsoo.co.tireerpserver.model.response.sale.SaleContentGridResponse;
import com.minsoo.co.tireerpserver.repository.sale.SaleContentRepository;
import com.minsoo.co.tireerpserver.repository.stock.StockRepository;
import com.minsoo.co.tireerpserver.repository.tire.TireDotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SaleContentService {

    private final SaleContentRepository saleContentRepository;
    private final TireDotRepository tireDotRepository;
    private final StockRepository stockRepository;

    public List<SaleContentGridResponse> findAllSaleContentGrids(SaleStatus saleStatus, SaleSource saleSource, SaleDateType saleDateType, LocalDate from, LocalDate to) {
        return saleContentRepository.findAllSaleContentGrids(saleStatus, saleSource, saleDateType, from, to);
    }

    public List<SaleContentGridResponse> findSaleContentGridsBySaleId(Long saleId) {
        return saleContentRepository.findSaleContentGridsBySaleId(saleId);
    }

    //TODO: sale-contents 가 tire-dot 와 stock 기준으로 unique 해야함. -> grouping 혹은 validation 할 것
    public void modifySaleContents(Sale sale, List<SaleContentRequest> saleContentRequests) {
        List<SaleContent> saleContents = saleContentRepository.findAllBySale(sale);

        List<Long> removable = saleContents.stream()
                .map(SaleContent::getId)
                .collect(Collectors.toList());
        List<Long> stored = new ArrayList<>();

        // save and update
        for (SaleContentRequest saleContentRequest : saleContentRequests) {
            TireDot tireDot = findTireDotById(saleContentRequest.getTireDotId());
            Stock stock = findStockById(saleContentRequest.getStockId());

            stored.add(saleContentRepository.findBySaleAndTireDotAndStock(sale, tireDot, stock)
                    .map(found -> found.update(saleContentRequest))
                    .orElseGet(() -> saleContentRepository.save(SaleContent.of(sale, tireDot, stock, saleContentRequest)))
                    .getId());
        }

        // remove
        removable.removeAll(stored);
        if (!CollectionUtils.isEmpty(removable)) {
            saleContentRepository.deleteAllByIdIn(removable);
        }
    }

    private TireDot findTireDotById(Long tireDotId) {
        return tireDotRepository.findById(tireDotId).orElseThrow(() -> new NotFoundException("타이어 DOT", tireDotId));
    }

    private Stock findStockById(Long stockId) {
        return stockRepository.findById(stockId).orElseThrow(() -> new NotFoundException("재고", stockId));
    }
}
