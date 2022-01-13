package com.minsoo.co.tireerpserver.service.sale;

import com.minsoo.co.tireerpserver.entity.sale.Sale;
import com.minsoo.co.tireerpserver.entity.sale.SaleMemo;
import com.minsoo.co.tireerpserver.exception.NotFoundException;
import com.minsoo.co.tireerpserver.model.request.sale.SaleMemoRequest;
import com.minsoo.co.tireerpserver.model.response.sale.SaleMemoResponse;
import com.minsoo.co.tireerpserver.repository.sale.SaleMemoRepository;
import com.minsoo.co.tireerpserver.repository.sale.SaleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SaleMemoService {

    private final SaleMemoRepository saleMemoRepository;
    private final SaleRepository saleRepository;

    public List<SaleMemoResponse> findAllBySale(Long saleId) {
        Sale sale = findSaleById(saleId);
        return saleMemoRepository.findAllBySale(sale).stream()
                .map(SaleMemoResponse::new)
                .collect(Collectors.toList());
    }

    public SaleMemoResponse create(Long saleId, SaleMemoRequest saleMemoRequest) {
        Sale sale = findSaleById(saleId);
        return new SaleMemoResponse(saleMemoRepository.save(SaleMemo.of(sale, saleMemoRequest)));
    }

    public SaleMemoResponse update(Long saleMemoId, SaleMemoRequest saleMemoRequest) {
        SaleMemo saleMemo = findSaleMemoById(saleMemoId);
        return new SaleMemoResponse(saleMemo.update(saleMemoRequest));
    }

    public void deleteById(Long saleMemoId) {
        SaleMemo saleMemo = findSaleMemoById(saleMemoId);
        saleMemoRepository.delete(saleMemo);
    }

    private Sale findSaleById(Long saleId) {
        return saleRepository.findById(saleId).orElseThrow(() -> new NotFoundException("매출", saleId));
    }

    private SaleMemo findSaleMemoById(Long saleMemoId) {
        return saleMemoRepository.findById(saleMemoId).orElseThrow(() -> new NotFoundException("매출 메모", saleMemoId));
    }
}
