package com.minsoo.co.tireerpserver.service.sale;

import com.minsoo.co.tireerpserver.constant.SystemMessage;
import com.minsoo.co.tireerpserver.entity.sale.Sale;
import com.minsoo.co.tireerpserver.entity.sale.SaleContent;
import com.minsoo.co.tireerpserver.exception.NotFoundException;
import com.minsoo.co.tireerpserver.repository.sale.SaleContentRepository;
import com.minsoo.co.tireerpserver.repository.sale.SaleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SaleContentService {

    private final SaleContentRepository saleContentRepository;
    private final SaleRepository saleRepository;

    public List<SaleContent> findAll() {
        return saleContentRepository.findAll();
    }

    public List<SaleContent> findAllBySaleId(Long saleId) {
        Sale sale = saleRepository.findById(saleId).orElseThrow(() -> {
            log.error("Can not find sale by id: {}", saleId);
            return new NotFoundException(SystemMessage.NOT_FOUND + ": [매출]");
        });
        return saleContentRepository.findAllBySale(sale);
    }
}
