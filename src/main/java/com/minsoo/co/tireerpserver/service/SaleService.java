package com.minsoo.co.tireerpserver.service;

import com.minsoo.co.tireerpserver.api.error.errors.NotFoundException;
import com.minsoo.co.tireerpserver.model.dto.sale.SaleCreateRequest;
import com.minsoo.co.tireerpserver.model.dto.sale.SaleResponse;
import com.minsoo.co.tireerpserver.model.dto.sale.SaleSimpleResponse;
import com.minsoo.co.tireerpserver.model.entity.Sale;
import com.minsoo.co.tireerpserver.model.entity.SaleContent;
import com.minsoo.co.tireerpserver.repository.CustomerRepository;
import com.minsoo.co.tireerpserver.repository.SaleContentRepository;
import com.minsoo.co.tireerpserver.repository.SaleRepository;
import com.minsoo.co.tireerpserver.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    public List<SaleResponse> findAll() {
        return saleRepository.findAll()
                .stream()
                .map(SaleResponse::of)
                .collect(Collectors.toList());
    }

    public SaleResponse findById(Long id) {
        return SaleResponse.of(saleRepository.findById(id).orElseThrow(NotFoundException::new));
    }

    @Transactional
    public SaleSimpleResponse create(SaleCreateRequest createRequest) {
        Sale sale = saleRepository.save(
                Sale.of(customerRepository.findById(createRequest.getCustomerId()).orElseThrow(NotFoundException::new),
                        createRequest.getSaleDate()));
        createRequest.getContents().forEach(content ->
                saleContentRepository.save(
                        SaleContent.of(sale,
                                stockRepository.findById(content.getStockId()).orElseThrow(NotFoundException::new).getTireDot(),
                                content.getQuantity(),
                                content.getPrice())));
        return SaleSimpleResponse.of(sale);
    }
}
