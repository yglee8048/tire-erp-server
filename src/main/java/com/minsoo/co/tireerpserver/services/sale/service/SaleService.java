package com.minsoo.co.tireerpserver.services.sale.service;

import com.minsoo.co.tireerpserver.api.error.exceptions.NotFoundException;
import com.minsoo.co.tireerpserver.api.v1.model.dto.sale.SaleCreateRequest;
import com.minsoo.co.tireerpserver.services.sale.entity.Sale;
import com.minsoo.co.tireerpserver.services.sale.entity.SaleContent;
import com.minsoo.co.tireerpserver.services.account.repository.CustomerRepository;
import com.minsoo.co.tireerpserver.services.sale.repository.SaleContentRepository;
import com.minsoo.co.tireerpserver.services.sale.repository.SaleRepository;
import com.minsoo.co.tireerpserver.services.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SaleService {

    private final SaleRepository saleRepository;
    private final SaleContentRepository saleContentRepository;
    private final CustomerRepository customerRepository;
    private final StockRepository stockRepository;

    public List<SaleContent> findAll() {
        return saleContentRepository.findAllOrderBySaleIdDesc();
    }

    public List<SaleContent> findById(Long id) {
        return saleContentRepository.findAllBySaleOrderBySaleDesc(
                saleRepository.findById(id).orElseThrow(NotFoundException::new));
    }

    @Transactional
    public Sale create(SaleCreateRequest createRequest) {
        Sale sale = saleRepository.save(
                Sale.of(customerRepository.findById(createRequest.getCustomerId()).orElseThrow(NotFoundException::new),
                        createRequest.getSaleDate()));
        createRequest.getContents().forEach(content ->
                saleContentRepository.save(
                        SaleContent.of(sale,
                                stockRepository.findById(content.getStockId()).orElseThrow(NotFoundException::new).getTireDot(),
                                content.getQuantity(),
                                content.getPrice())));
        return sale;
    }
}
