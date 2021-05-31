package com.minsoo.co.tireerpserver.service.sale;

import com.minsoo.co.tireerpserver.api.error.exceptions.NotFoundException;
import com.minsoo.co.tireerpserver.model.dto.sale.SaleCreateRequest;
import com.minsoo.co.tireerpserver.model.entity.sale.Sale;
import com.minsoo.co.tireerpserver.model.entity.sale.SaleContent;
import com.minsoo.co.tireerpserver.repository.account.CustomerRepository;
import com.minsoo.co.tireerpserver.repository.sale.SaleContentRepository;
import com.minsoo.co.tireerpserver.repository.sale.SaleRepository;
import com.minsoo.co.tireerpserver.repository.stock.StockRepository;
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
