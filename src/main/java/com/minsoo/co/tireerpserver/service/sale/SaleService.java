package com.minsoo.co.tireerpserver.service.sale;

import com.minsoo.co.tireerpserver.constant.SaleSource;
import com.minsoo.co.tireerpserver.constant.SystemMessage;
import com.minsoo.co.tireerpserver.entity.client.Client;
import com.minsoo.co.tireerpserver.entity.client.ClientCompany;
import com.minsoo.co.tireerpserver.entity.sale.Sale;
import com.minsoo.co.tireerpserver.entity.sale.SaleContent;
import com.minsoo.co.tireerpserver.entity.sale.SaleMemo;
import com.minsoo.co.tireerpserver.entity.stock.Stock;
import com.minsoo.co.tireerpserver.exception.BadRequestException;
import com.minsoo.co.tireerpserver.exception.NotFoundException;
import com.minsoo.co.tireerpserver.model.request.customer.sale.CustomerSaleCreateRequest;
import com.minsoo.co.tireerpserver.model.request.sale.SaleContentRequest;
import com.minsoo.co.tireerpserver.model.request.sale.SaleCreateRequest;
import com.minsoo.co.tireerpserver.model.request.sale.SaleMemoRequest;
import com.minsoo.co.tireerpserver.model.request.sale.SaleUpdateRequest;
import com.minsoo.co.tireerpserver.model.response.sale.SaleResponse;
import com.minsoo.co.tireerpserver.repository.client.ClientCompanyRepository;
import com.minsoo.co.tireerpserver.repository.client.ClientRepository;
import com.minsoo.co.tireerpserver.repository.rank.RankDotPriceRepository;
import com.minsoo.co.tireerpserver.repository.sale.SaleMemoRepository;
import com.minsoo.co.tireerpserver.repository.sale.SaleRepository;
import com.minsoo.co.tireerpserver.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SaleService {

    private final SaleRepository saleRepository;
    private final ClientRepository clientRepository;
    private final ClientCompanyRepository clientCompanyRepository;
    private final RankDotPriceRepository rankDotPriceRepository;
    private final SaleContentService saleContentService;
    private final SaleMemoRepository saleMemoRepository;

    public SaleResponse findById(Long saleId) {
        return new SaleResponse(findSaleById(saleId));
    }

    public SaleResponse create(SaleCreateRequest saleCreateRequest, SaleSource source) {
        ClientCompany clientCompany = findClientCompanyById(saleCreateRequest.getClientCompanyId());
        Sale sale = saleRepository.save(Sale.of(clientCompany, saleCreateRequest, source));

        saleContentService.modifySaleContents(sale, saleCreateRequest.getContents());
        addSaleMemos(sale, saleCreateRequest);

        return new SaleResponse(sale);
    }

    public SaleResponse update(Long saleId, SaleUpdateRequest saleUpdateRequest, SaleSource source) {
        Sale sale = findSaleById(saleId);
        ClientCompany clientCompany = findClientCompanyById(saleUpdateRequest.getClientCompanyId());
        sale.update(clientCompany, saleUpdateRequest, source);

        saleContentService.modifySaleContents(sale, saleUpdateRequest.getContents());

        return new SaleResponse(sale);
    }

    public SaleResponse confirm(Long saleId) {
        Sale sale = findSaleById(saleId);

        for (SaleContent saleContent : sale.getSaleContents()) {
            Stock stock = saleContent.getStock();
            if (stock == null) {
                throw new BadRequestException(SystemMessage.STOCK_NOT_SELECTED);
            }
        }
        return new SaleResponse(sale.confirm());
    }

    public SaleResponse complete(Long saleId) {
        Sale sale = findSaleById(saleId);
        return new SaleResponse(sale.completed());
    }

    public void deleteById(Long saleId) {
        Sale sale = findSaleById(saleId);
        saleRepository.delete(sale);
    }

    private Sale findSaleById(Long saleId) {
        return saleRepository.findById(saleId).orElseThrow(() -> new NotFoundException("매출", saleId));
    }

    private ClientCompany findClientCompanyById(Long clientCompanyId) {
        return clientCompanyRepository.findById(clientCompanyId).orElseThrow(() -> new NotFoundException("고객사", clientCompanyId));
    }

    private void addSaleMemos(Sale sale, SaleCreateRequest saleCreateRequest) {
        if (!CollectionUtils.isEmpty(saleCreateRequest.getMemos())) {
            for (SaleMemoRequest saleMemoRequest : saleCreateRequest.getMemos()) {
                saleMemoRepository.save(SaleMemo.of(sale, saleMemoRequest));
            }
        }
    }
}
