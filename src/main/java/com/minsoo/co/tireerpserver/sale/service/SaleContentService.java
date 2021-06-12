package com.minsoo.co.tireerpserver.sale.service;

import com.minsoo.co.tireerpserver.sale.entity.Sale;
import com.minsoo.co.tireerpserver.sale.entity.SaleContent;
import com.minsoo.co.tireerpserver.sale.model.SaleContentGridResponse;
import com.minsoo.co.tireerpserver.sale.repository.SaleContentRepository;
import com.minsoo.co.tireerpserver.sale.repository.SaleRepository;
import com.minsoo.co.tireerpserver.shared.error.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SaleContentService {

    private final SaleRepository saleRepository;
    private final SaleContentRepository saleContentRepository;

    public List<SaleContent> findAllBySaleId(Long saleId) {
        Sale sale = saleRepository.findById(saleId).orElseThrow(() -> new NotFoundException("매출", saleId));
        return saleContentRepository.findAllBySale(sale);
    }

    public SaleContent findById(Long saleContentId) {
        return saleContentRepository.findById(saleContentId).orElseThrow(() -> new NotFoundException("매출 항목", saleContentId));
    }

    public List<SaleContentGridResponse> findAllByTransactionDate(LocalDate from, LocalDate to) {
        return saleContentRepository.findSaleContents(from, to);
    }
}
