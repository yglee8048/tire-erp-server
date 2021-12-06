package com.minsoo.co.tireerpserver.service.sale;

import com.minsoo.co.tireerpserver.constant.SaleSource;
import com.minsoo.co.tireerpserver.constant.SystemMessage;
import com.minsoo.co.tireerpserver.entity.account.ClientCompany;
import com.minsoo.co.tireerpserver.entity.sale.Sale;
import com.minsoo.co.tireerpserver.entity.sale.SaleContent;
import com.minsoo.co.tireerpserver.entity.sale.SaleMemo;
import com.minsoo.co.tireerpserver.entity.tire.TireDot;
import com.minsoo.co.tireerpserver.exception.NotFoundException;
import com.minsoo.co.tireerpserver.model.request.sale.SaleContentRequest;
import com.minsoo.co.tireerpserver.model.request.sale.SaleMemoRequest;
import com.minsoo.co.tireerpserver.model.request.sale.SaleRequest;
import com.minsoo.co.tireerpserver.repository.account.ClientCompanyRepository;
import com.minsoo.co.tireerpserver.repository.sale.SaleContentRepository;
import com.minsoo.co.tireerpserver.repository.sale.SaleMemoRepository;
import com.minsoo.co.tireerpserver.repository.sale.SaleRepository;
import com.minsoo.co.tireerpserver.repository.tire.TireDotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SaleService {

    private final SaleRepository saleRepository;
    private final SaleContentRepository saleContentRepository;
    private final SaleMemoRepository saleMemoRepository;
    private final ClientCompanyRepository clientCompanyRepository;
    private final TireDotRepository tireDotRepository;

    public Sale findById(Long saleId) {
        return saleRepository.findById(saleId).orElseThrow(() -> {
            log.error("Can not find sale by id: {}", saleId);
            return new NotFoundException(SystemMessage.NOT_FOUND + ": [매출]");
        });
    }

    public Sale create(SaleRequest saleRequest, SaleSource source) {
        ClientCompany clientCompany = findClientCompanyById(saleRequest.getClientCompanyId());
        Sale sale = saleRepository.save(Sale.of(clientCompany, saleRequest, source));

        addSaleContents(sale, saleRequest);
        addSaleMemos(sale, saleRequest);

        return sale;
    }

    public Sale update(Long saleId, SaleRequest saleRequest) {
        Sale sale = findById(saleId);
        ClientCompany clientCompany = findClientCompanyById(saleRequest.getClientCompanyId());
        sale.update(clientCompany, saleRequest);

        sale.getSaleContents().clear();
        addSaleContents(sale, saleRequest);

        sale.getSaleMemos().clear();
        addSaleMemos(sale, saleRequest);

        return sale;
    }

    public void deleteById(Long saleId) {
        Sale sale = findById(saleId);
        saleRepository.delete(sale);
    }

    private void addSaleContents(Sale sale, SaleRequest saleRequest) {
        for (SaleContentRequest content : saleRequest.getContents()) {
            TireDot tireDot = tireDotRepository.findById(content.getTireDotId()).orElseThrow(() -> {
                log.error("Can not find tire dot by id: {}", content.getTireDotId());
                return new NotFoundException(SystemMessage.NOT_FOUND + ": [타이어 DOT]");
            });
            SaleContent saleContent = saleContentRepository.save(SaleContent.of(sale, tireDot, content));
            sale.getSaleContents().add(saleContent);
        }
    }

    private void addSaleMemos(Sale sale, SaleRequest saleRequest) {
        if (!CollectionUtils.isEmpty(saleRequest.getMemos())) {
            for (SaleMemoRequest saleMemoRequest : saleRequest.getMemos()) {
                SaleMemo saleMemo = saleMemoRepository.save(SaleMemo.of(sale, saleMemoRequest));
                sale.getSaleMemos().add(saleMemo);
            }
        }
    }

    private ClientCompany findClientCompanyById(Long clientCompanyId) {
        return clientCompanyRepository.findById(clientCompanyId).orElseThrow(() -> {
            log.error("Can not find client company by id: {}", clientCompanyId);
            return new NotFoundException(SystemMessage.NOT_FOUND + ": [고객사]");
        });
    }
}
