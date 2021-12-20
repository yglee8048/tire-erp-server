package com.minsoo.co.tireerpserver.service.sale;

import com.minsoo.co.tireerpserver.constant.SaleSource;
import com.minsoo.co.tireerpserver.constant.SystemMessage;
import com.minsoo.co.tireerpserver.entity.client.ClientCompany;
import com.minsoo.co.tireerpserver.entity.sale.Sale;
import com.minsoo.co.tireerpserver.entity.sale.SaleContent;
import com.minsoo.co.tireerpserver.entity.sale.SaleMemo;
import com.minsoo.co.tireerpserver.entity.stock.Stock;
import com.minsoo.co.tireerpserver.entity.tire.TireDot;
import com.minsoo.co.tireerpserver.exception.BadRequestException;
import com.minsoo.co.tireerpserver.exception.NotFoundException;
import com.minsoo.co.tireerpserver.model.request.sale.SaleContentRequest;
import com.minsoo.co.tireerpserver.model.request.sale.SaleMemoRequest;
import com.minsoo.co.tireerpserver.model.request.sale.SaleRequest;
import com.minsoo.co.tireerpserver.repository.client.ClientCompanyRepository;
import com.minsoo.co.tireerpserver.repository.sale.SaleContentRepository;
import com.minsoo.co.tireerpserver.repository.sale.SaleMemoRepository;
import com.minsoo.co.tireerpserver.repository.sale.SaleRepository;
import com.minsoo.co.tireerpserver.repository.stock.StockRepository;
import com.minsoo.co.tireerpserver.repository.tire.TireDotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Optional;

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
    private final StockRepository stockRepository;

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

    public Sale confirm(Long saleId) {
        Sale sale = findById(saleId);

        for (SaleContent saleContent : sale.getSaleContents()) {
            Stock stock = saleContent.getStock();
            if (stock == null) {
                throw new BadRequestException(SystemMessage.STOCK_NOT_SELECTED);
            }

            stock.reduceQuantity(saleContent.getQuantity());
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

    private void addSaleContents(Sale sale, SaleRequest saleRequest) {
        for (SaleContentRequest content : saleRequest.getContents()) {
            TireDot tireDot = tireDotRepository.findById(content.getTireDotId()).orElseThrow(() -> {
                log.error("Can not find tire dot by id: {}", content.getTireDotId());
                return new NotFoundException(SystemMessage.NOT_FOUND + ": [타이어 DOT]");
            });
            Stock stock = Optional.ofNullable(content.getStockId())
                    .map(stockId -> stockRepository.findById(stockId).orElseThrow(() -> {
                        log.error("Can not find stock by id: {}", stockId);
                        return new NotFoundException(SystemMessage.NOT_FOUND + ": [재고]");
                    }))
                    .orElse(null);
            SaleContent saleContent = saleContentRepository.save(SaleContent.of(sale, tireDot, stock, content));
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
