package com.minsoo.co.tireerpserver.sale.service;

import com.minsoo.co.tireerpserver.sale.entity.Sale;
import com.minsoo.co.tireerpserver.sale.entity.SaleMemo;
import com.minsoo.co.tireerpserver.sale.model.memo.SaleMemoRequest;
import com.minsoo.co.tireerpserver.sale.repository.SaleMemoRepository;
import com.minsoo.co.tireerpserver.sale.repository.SaleRepository;
import com.minsoo.co.tireerpserver.shared.error.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SaleMemoService {

    private final SaleRepository saleRepository;
    private final SaleMemoRepository saleMemoRepository;

    public List<SaleMemo> findAllBySaleId(Long saleId) {
        Sale sale = saleRepository.findById(saleId).orElseThrow(() -> new NotFoundException("매출", saleId));
        return saleMemoRepository.findAllBySale(sale);
    }

    public SaleMemo findById(Long saleMemoId) {
        return saleMemoRepository.findById(saleMemoId).orElseThrow(() -> new NotFoundException("매출 메모", saleMemoId));
    }

    public SaleMemo create(Long saleId, SaleMemoRequest saleMemoRequest) {
        Sale sale = saleRepository.findById(saleId).orElseThrow(() -> new NotFoundException("매출", saleId));
        return saleMemoRepository.save(SaleMemo.of(sale, saleMemoRequest));
    }

    public SaleMemo update(Long saleMemoId, SaleMemoRequest saleMemoRequest) {
        SaleMemo saleMemo = saleMemoRepository.findById(saleMemoId).orElseThrow(() -> new NotFoundException("매출 메모", saleMemoId));
        return saleMemo.update(saleMemoRequest);
    }

    public void removeById(Long saleMemoId) {
        SaleMemo saleMemo = saleMemoRepository.findById(saleMemoId).orElseThrow(() -> new NotFoundException("매출 메모", saleMemoId));
        saleMemoRepository.delete(saleMemo);
    }
}
