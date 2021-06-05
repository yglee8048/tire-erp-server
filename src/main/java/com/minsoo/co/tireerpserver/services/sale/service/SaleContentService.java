package com.minsoo.co.tireerpserver.services.sale.service;

import com.minsoo.co.tireerpserver.api.v1.model.dto.sale.SaleContentGridResponse;
import com.minsoo.co.tireerpserver.services.sale.repository.SaleContentRepository;
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

    private final SaleContentRepository saleContentRepository;

    public List<SaleContentGridResponse> findAllByTransactionDate(LocalDate from, LocalDate to) {
        return saleContentRepository.findSaleContents(from, to);
    }
}
