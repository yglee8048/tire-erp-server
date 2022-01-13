package com.minsoo.co.tireerpserver.service.customer;

import com.minsoo.co.tireerpserver.constant.SaleSource;
import com.minsoo.co.tireerpserver.constant.SaleStatus;
import com.minsoo.co.tireerpserver.constant.SystemMessage;
import com.minsoo.co.tireerpserver.entity.client.Client;
import com.minsoo.co.tireerpserver.entity.client.ClientCompany;
import com.minsoo.co.tireerpserver.entity.sale.Sale;
import com.minsoo.co.tireerpserver.exception.BadRequestException;
import com.minsoo.co.tireerpserver.exception.NotFoundException;
import com.minsoo.co.tireerpserver.model.request.customer.sale.CustomerSaleContentRequest;
import com.minsoo.co.tireerpserver.model.request.customer.sale.CustomerSaleCreateRequest;
import com.minsoo.co.tireerpserver.model.request.customer.sale.CustomerSaleUpdateRequest;
import com.minsoo.co.tireerpserver.model.request.sale.SaleContentRequest;
import com.minsoo.co.tireerpserver.model.request.sale.SaleCreateRequest;
import com.minsoo.co.tireerpserver.model.request.sale.SaleDateType;
import com.minsoo.co.tireerpserver.model.request.sale.SaleUpdateRequest;
import com.minsoo.co.tireerpserver.model.response.client.ClientCompanyResponse;
import com.minsoo.co.tireerpserver.model.response.client.ClientResponse;
import com.minsoo.co.tireerpserver.model.response.tire.TireDotGridResponse;
import com.minsoo.co.tireerpserver.model.response.sale.CustomerSaleContentGridResponse;
import com.minsoo.co.tireerpserver.model.response.tire.CustomerTireGridResponse;
import com.minsoo.co.tireerpserver.model.response.sale.SaleResponse;
import com.minsoo.co.tireerpserver.model.response.stock.StockGridResponse;
import com.minsoo.co.tireerpserver.repository.client.ClientCompanyRepository;
import com.minsoo.co.tireerpserver.repository.client.ClientRepository;
import com.minsoo.co.tireerpserver.repository.rank.RankDotPriceRepository;
import com.minsoo.co.tireerpserver.repository.sale.SaleContentRepository;
import com.minsoo.co.tireerpserver.repository.sale.SaleRepository;
import com.minsoo.co.tireerpserver.service.sale.SaleService;
import com.minsoo.co.tireerpserver.service.stock.StockService;
import com.minsoo.co.tireerpserver.service.tire.TireDotService;
import com.minsoo.co.tireerpserver.service.tire.TireService;
import com.minsoo.co.tireerpserver.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CustomerService {

    private final TireService tireService;
    private final TireDotService tireDotService;
    private final SaleService saleService;
    private final StockService stockService;

    private final SaleRepository saleRepository;
    private final ClientRepository clientRepository;
    private final ClientCompanyRepository clientCompanyRepository;
    private final SaleContentRepository saleContentRepository;
    private final RankDotPriceRepository rankDotPriceRepository;

    // CLIENT
    public ClientResponse findContextUser() {
        String contextUsername = SecurityUtils.getContextUsername();
        return new ClientResponse(findClientByUsername(contextUsername));
    }

    public ClientCompanyResponse findContextUserCompany() {
        Client client = findClientByUsername(SecurityUtils.getContextUsername());
        return new ClientCompanyResponse(findClientCompanyById(client.getClientCompany().getId()));
    }

    // TIRE
    public List<CustomerTireGridResponse> findCustomerTireGrids() {
        return tireService.findAllTireGrids().stream()
                .map(CustomerTireGridResponse::new)
                .collect(Collectors.toList());
    }

    public List<TireDotGridResponse> findTireDotGridsByTireId(Long tireId) {
        Client client = findClientByUsername(SecurityUtils.getContextUsername());
        return tireDotService.findTireDotGridsByTireIdAndOptionalClientCompanyId(tireId, client.getClientCompany().getId());
    }

    // STOCK
    public List<StockGridResponse> findStockGridsByTireDotId(Long tireDotId) {
        return stockService.findStockGridsByTireDotId(tireDotId)
                .stream()
                .filter(stockGridResponse -> !stockGridResponse.getLock())
                .filter(stockGridResponse -> stockGridResponse.getQuantity() > 0)
                .collect(Collectors.toList());
    }

    // SALE
    public List<CustomerSaleContentGridResponse> findCustomerSaleContentGrids(SaleStatus saleStatus, SaleSource saleSource, SaleDateType saleDateType, LocalDate from, LocalDate to) {
        Client client = findClientByUsername(SecurityUtils.getContextUsername());
        return saleContentRepository.findSaleContentGridsByClientCompanyId(client.getClientCompany().getId(), saleStatus, saleSource, saleDateType, from, to)
                .stream()
                .map(CustomerSaleContentGridResponse::new)
                .collect(Collectors.toList());
    }

    public List<CustomerSaleContentGridResponse> findCustomerSaleContentGridsBySaleId(Long saleId) {
        return saleContentRepository.findSaleContentGridsBySaleId(saleId).stream()
                .map(CustomerSaleContentGridResponse::new)
                .collect(Collectors.toList());
    }

    public SaleResponse create(CustomerSaleCreateRequest customerSaleCreateRequest) {
        String contextUsername = SecurityUtils.getContextUsername();
        Client client = clientRepository.findByUsername(contextUsername).orElseThrow(() -> new NotFoundException("고객 계정", contextUsername));
        ClientCompany clientCompany = findClientCompanyById(client.getClientCompany().getId());

        List<SaleContentRequest> contents = toSaleContents(customerSaleCreateRequest.getContents(), clientCompany);
        return saleService.create(new SaleCreateRequest(clientCompany.getId(), customerSaleCreateRequest, contents), SaleSource.ONLINE);
    }

    public SaleResponse update(Long saleId, CustomerSaleUpdateRequest customerSaleUpdateRequest) {
        String contextUsername = SecurityUtils.getContextUsername();
        Client client = clientRepository.findByUsername(contextUsername).orElseThrow(() -> new NotFoundException("고객 계정", contextUsername));
        ClientCompany clientCompany = findClientCompanyById(client.getClientCompany().getId());

        List<SaleContentRequest> contents = toSaleContents(customerSaleUpdateRequest.getContents(), clientCompany);
        return saleService.update(saleId, new SaleUpdateRequest(clientCompany.getId(), customerSaleUpdateRequest, contents), SaleSource.ONLINE);
    }

    public void deleteSaleById(Long saleId) {
        Sale sale = findSaleById(saleId);
        if (!sale.getStatus().equals(SaleStatus.REQUESTED)) {
            throw new BadRequestException(SystemMessage.ALREADY_CONFIRMED);
        }
        saleRepository.delete(sale);
    }

    private List<SaleContentRequest> toSaleContents(List<CustomerSaleContentRequest> customerSaleContentRequests, ClientCompany clientCompany) {
        return customerSaleContentRequests.stream()
                .map(customerSaleContentRequest -> {
                    Long price = rankDotPriceRepository.getPriceByTireDotIdAndClientId(customerSaleContentRequest.getTireDotId(), clientCompany.getRank().getId());
                    return new SaleContentRequest(customerSaleContentRequest, price);
                })
                .collect(Collectors.toList());
    }

    private Sale findSaleById(Long saleId) {
        return saleRepository.findById(saleId).orElseThrow(() -> new NotFoundException("매출", saleId));
    }

    private Client findClientByUsername(String username) {
        return clientRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("고객 계정", username));
    }

    private ClientCompany findClientCompanyById(Long clientCompanyId) {
        return clientCompanyRepository.findById(clientCompanyId).orElseThrow(() -> new NotFoundException("고객사", clientCompanyId));
    }
}
