package com.minsoo.co.tireerpserver.sale.service;

import com.minsoo.co.tireerpserver.sale.entity.SaleContent;
import com.minsoo.co.tireerpserver.sale.model.content.SaleContentConfirmRequest;
import com.minsoo.co.tireerpserver.sale.model.content.SaleContentStockRequest;
import com.minsoo.co.tireerpserver.shared.error.exceptions.AlreadyConfirmedException;
import com.minsoo.co.tireerpserver.shared.error.exceptions.BadRequestException;
import com.minsoo.co.tireerpserver.shared.error.exceptions.NotFoundException;
import com.minsoo.co.tireerpserver.sale.model.SaleRequest;
import com.minsoo.co.tireerpserver.sale.model.content.SaleContentRequest;
import com.minsoo.co.tireerpserver.user.entity.Customer;
import com.minsoo.co.tireerpserver.sale.code.SaleStatus;
import com.minsoo.co.tireerpserver.sale.entity.Sale;
import com.minsoo.co.tireerpserver.user.repository.CustomerRepository;
import com.minsoo.co.tireerpserver.sale.repository.SaleContentRepository;
import com.minsoo.co.tireerpserver.sale.repository.SaleRepository;
import com.minsoo.co.tireerpserver.stock.entity.Stock;
import com.minsoo.co.tireerpserver.stock.repository.StockRepository;
import com.minsoo.co.tireerpserver.tire.entity.TireDot;
import com.minsoo.co.tireerpserver.tire.repository.TireDotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SaleService {

    private final SaleRepository saleRepository;
    private final SaleContentRepository saleContentRepository;
    private final CustomerRepository customerRepository;
    private final StockRepository stockRepository;
    private final TireDotRepository tireDotRepository;

    public List<Sale> findAll() {
        return saleRepository.findAll();
    }

    public Sale findById(Long id) {
        return saleRepository.findById(id).orElseThrow(() -> new NotFoundException("매출", id));
    }

    @Transactional
    public Sale create(SaleRequest saleRequest) {
        Customer customer = customerRepository.findById(saleRequest.getCustomerId())
                .orElseThrow(() -> new NotFoundException("고객", saleRequest.getCustomerId()));

        return saleRepository.save(Sale.of(customer, saleRequest, makeContentMap(saleRequest)));
    }

    @Transactional
    public Sale update(Long saleId, SaleRequest saleRequest) {
        Sale sale = saleRepository.findById(saleId).orElseThrow(() -> new NotFoundException("매출", saleId));
        Customer customer = customerRepository.findById(saleRequest.getCustomerId())
                .orElseThrow(() -> new NotFoundException("고객", saleRequest.getCustomerId()));
        // validation: 이미 확정된 매출 건은 수정할 수 없다.
        if (sale.getStatus().equals(SaleStatus.CONFIRMED)) {
            throw new AlreadyConfirmedException();
        }

        sale.update(customer, saleRequest, makeContentMap(saleRequest));
        return sale;
    }

    private Map<TireDot, List<SaleContentRequest>> makeContentMap(SaleRequest saleRequest) {
        return saleRequest.getContents()
                .stream()
                .collect(Collectors.groupingBy(
                        contentRequest -> tireDotRepository.findById(contentRequest.getTireDotId())
                                .orElseThrow(() -> new NotFoundException("타이어 DOT", contentRequest.getTireDotId()))));
    }

    @Transactional
    public Sale confirm(Long saleId, List<SaleContentConfirmRequest> contentConfirmRequests) {
        Sale sale = saleRepository.findById(saleId).orElseThrow(() -> new NotFoundException("매출", saleId));
        if (sale.getContents().size() != contentConfirmRequests.size()) {
            throw new BadRequestException("매출 항목이 모두 확정되어야 합니다.");
        }

        contentConfirmRequests.forEach(contentConfirmRequest -> {
            SaleContent saleContent = saleContentRepository.findById(contentConfirmRequest.getSaleContentId())
                    .orElseThrow(() -> new NotFoundException("매출 항목", contentConfirmRequest.getSaleContentId()));

            Long sumOfStock = contentConfirmRequest.getStockRequests().stream()
                    .map(SaleContentStockRequest::getQuantity)
                    .reduce(0L, Long::sum);
            if (!sumOfStock.equals(saleContent.getQuantity())) {
                throw new BadRequestException("입력된 재고의 개수가 적절하지 않습니다.");
            }

            contentConfirmRequest.getStockRequests().forEach(
                    saleContentStockRequest -> {
                        Stock stock = stockRepository.findById(saleContentStockRequest.getStockId())
                                .orElseThrow(() -> new NotFoundException("재고", saleContentStockRequest.getStockId()));
                        stock.reduceQuantity(saleContentStockRequest.getQuantity());
                    });
        });

        return sale.confirmStatus();
    }

    @Transactional
    public Sale cancelById(Long saleId) {
        Sale sale = saleRepository.findById(saleId).orElseThrow(() -> new NotFoundException("매출", saleId));
        return sale.cancel();
    }
}
