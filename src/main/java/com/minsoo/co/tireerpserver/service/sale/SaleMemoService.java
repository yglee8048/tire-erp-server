package com.minsoo.co.tireerpserver.service.sale;

import com.minsoo.co.tireerpserver.constant.SystemMessage;
import com.minsoo.co.tireerpserver.entity.sale.Sale;
import com.minsoo.co.tireerpserver.entity.sale.SaleMemo;
import com.minsoo.co.tireerpserver.exception.NotFoundException;
import com.minsoo.co.tireerpserver.model.request.sale.SaleMemoRequest;
import com.minsoo.co.tireerpserver.repository.sale.SaleMemoRepository;
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
public class SaleMemoService {

    private final SaleMemoRepository saleMemoRepository;
    private final SaleRepository saleRepository;

    public List<SaleMemo> findAllBySale(Long saleId) {
        Sale sale = findSaleById(saleId);
        return saleMemoRepository.findAllBySale(sale);
    }

    public SaleMemo findById(Long saleMemoId) {
        return saleMemoRepository.findById(saleMemoId).orElseThrow(() -> {
            log.error("Can not find sale memo by id: {}", saleMemoId);
            return new NotFoundException(SystemMessage.NOT_FOUND + ": [매출 메모]");
        });
    }

    public SaleMemo create(Long saleId, SaleMemoRequest saleMemoRequest) {
        Sale sale = findSaleById(saleId);
        return saleMemoRepository.save(SaleMemo.of(sale, saleMemoRequest));
    }

    public SaleMemo update(Long saleMemoId, SaleMemoRequest saleMemoRequest) {
        SaleMemo saleMemo = findById(saleMemoId);
        return saleMemo.update(saleMemoRequest);
    }

    public void deleteById(Long saleMemoId) {
        SaleMemo saleMemo = findById(saleMemoId);
        saleMemoRepository.delete(saleMemo);
    }

    private Sale findSaleById(Long saleId) {
        return saleRepository.findById(saleId).orElseThrow(() -> {
            log.error("Can not find sale by id: {}", saleId);
            return new NotFoundException(SystemMessage.NOT_FOUND + ": [매출]");
        });
    }
}
