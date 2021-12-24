package com.minsoo.co.tireerpserver.service.sale;

import com.minsoo.co.tireerpserver.constant.SaleSource;
import com.minsoo.co.tireerpserver.constant.SystemMessage;
import com.minsoo.co.tireerpserver.entity.client.ClientCompany;
import com.minsoo.co.tireerpserver.entity.sale.Sale;
import com.minsoo.co.tireerpserver.entity.sale.SaleContent;
import com.minsoo.co.tireerpserver.entity.sale.SaleMemo;
import com.minsoo.co.tireerpserver.entity.stock.Stock;
import com.minsoo.co.tireerpserver.exception.BadRequestException;
import com.minsoo.co.tireerpserver.exception.NotFoundException;
import com.minsoo.co.tireerpserver.model.request.sale.SaleCreateRequest;
import com.minsoo.co.tireerpserver.model.request.sale.SaleMemoRequest;
import com.minsoo.co.tireerpserver.model.request.sale.SaleUpdateRequest;
import com.minsoo.co.tireerpserver.repository.client.ClientCompanyRepository;
import com.minsoo.co.tireerpserver.repository.sale.SaleMemoRepository;
import com.minsoo.co.tireerpserver.repository.sale.SaleRepository;
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
    private final ClientCompanyRepository clientCompanyRepository;
    private final SaleContentService saleContentService;
    private final SaleMemoRepository saleMemoRepository;

    public Sale findById(Long saleId) {
        return saleRepository.findById(saleId).orElseThrow(() -> {
            log.error("Can not find sale by id: {}", saleId);
            return new NotFoundException(SystemMessage.NOT_FOUND + ": [매출]");
        });
    }

    public Sale create(SaleCreateRequest saleCreateRequest, SaleSource source) {
        ClientCompany clientCompany = findClientCompanyById(saleCreateRequest.getClientCompanyId());
        Sale sale = saleRepository.save(Sale.of(clientCompany, saleCreateRequest, source));

        saleContentService.modifySaleContents(sale, saleCreateRequest.getContents());
        addSaleMemos(sale, saleCreateRequest);

        return sale;
    }

    public Sale update(Long saleId, SaleUpdateRequest saleUpdateRequest) {
        Sale sale = findById(saleId);
        ClientCompany clientCompany = findClientCompanyById(saleUpdateRequest.getClientCompanyId());
        sale.update(clientCompany, saleUpdateRequest);

        saleContentService.modifySaleContents(sale, saleUpdateRequest.getContents());

        return sale;
    }

    public Sale confirm(Long saleId) {
        Sale sale = findById(saleId);

        for (SaleContent saleContent : sale.getSaleContents()) {
            Stock stock = saleContent.getStock();
            if (stock == null) {
                throw new BadRequestException(SystemMessage.STOCK_NOT_SELECTED);
            }
        }
        return sale.confirm();
    }

    public Sale complete(Long saleId) {
        Sale sale = findById(saleId);
        return sale.completed();
    }

    public void deleteById(Long saleId) {
        Sale sale = findById(saleId);
        saleRepository.delete(sale);
    }

    private void addSaleMemos(Sale sale, SaleCreateRequest saleCreateRequest) {
        if (!CollectionUtils.isEmpty(saleCreateRequest.getMemos())) {
            for (SaleMemoRequest saleMemoRequest : saleCreateRequest.getMemos()) {
                saleMemoRepository.save(SaleMemo.of(sale, saleMemoRequest));
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
