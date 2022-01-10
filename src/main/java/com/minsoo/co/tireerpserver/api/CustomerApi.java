package com.minsoo.co.tireerpserver.api;

import com.minsoo.co.tireerpserver.constant.SaleSource;
import com.minsoo.co.tireerpserver.constant.SaleStatus;
import com.minsoo.co.tireerpserver.constant.SystemMessage;
import com.minsoo.co.tireerpserver.entity.client.Client;
import com.minsoo.co.tireerpserver.entity.client.ClientCompany;
import com.minsoo.co.tireerpserver.model.ApiResponse;
import com.minsoo.co.tireerpserver.model.request.customer.sale.CustomerSaleCreateRequest;
import com.minsoo.co.tireerpserver.model.request.customer.sale.CustomerSaleUpdateRequest;
import com.minsoo.co.tireerpserver.model.request.sale.SaleCreateRequest;
import com.minsoo.co.tireerpserver.model.request.sale.SaleDateType;
import com.minsoo.co.tireerpserver.model.response.client.ClientCompanyResponse;
import com.minsoo.co.tireerpserver.model.response.client.ClientResponse;
import com.minsoo.co.tireerpserver.model.response.grid.customer.CustomerSaleContentGridResponse;
import com.minsoo.co.tireerpserver.model.response.grid.customer.CustomerTireDotGridResponse;
import com.minsoo.co.tireerpserver.model.response.grid.customer.CustomerTireGridResponse;
import com.minsoo.co.tireerpserver.model.response.sale.SaleResponse;
import com.minsoo.co.tireerpserver.service.client.ClientCompanyService;
import com.minsoo.co.tireerpserver.service.client.ClientService;
import com.minsoo.co.tireerpserver.service.grid.GridService;
import com.minsoo.co.tireerpserver.service.sale.SaleService;
import com.minsoo.co.tireerpserver.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/customer/api/v1")
@RequiredArgsConstructor
public class CustomerApi {

    private final GridService gridService;
    private final ClientCompanyService clientCompanyService;
    private final ClientService clientService;
    private final SaleService saleService;

    @GetMapping("/client")
    public ApiResponse<ClientResponse> findClient() {
        return ApiResponse.OK(new ClientResponse(getClientFromContext()));
    }

    @GetMapping("/client-company")
    public ApiResponse<ClientCompanyResponse> findClientCompany() {
        return ApiResponse.OK(new ClientCompanyResponse(getClientCompanyFromContext()));
    }

    @GetMapping("/tire-grids")
    public ApiResponse<List<CustomerTireGridResponse>> findCustomerTireGrids() {
        return ApiResponse.OK(gridService.findAllCustomerTireGrids());
    }

    @GetMapping("/tires/{tireId}/tire-dot-grids")
    public ApiResponse<List<CustomerTireDotGridResponse>> findCustomerTireDotGrids(@PathVariable Long tireId) {
        ClientCompany clientCompany = getClientCompanyFromContext();
        return ApiResponse.OK(gridService.findCustomerTireDotGridsByTireIdAndRankId(tireId, clientCompany.getRank().getId()));
    }

    @GetMapping("/sale-content-grids")
    public ApiResponse<List<CustomerSaleContentGridResponse>> findCustomerSaleGrids(@RequestParam(required = false) SaleStatus status,
                                                                                    @RequestParam(required = false) SaleSource source,
                                                                                    @RequestParam(required = false) SaleDateType saleDateType,
                                                                                    @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
                                                                                    @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to) {
        Client client = getClientFromContext();
        Long clientCompanyId = client.getClientCompany().getId();
        return ApiResponse.OK(gridService.findCustomerSaleContentGridsByClientCompanyId(clientCompanyId, status, source, saleDateType, from, to));
    }

    @PostMapping("/sales")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<SaleResponse> createSale(@RequestBody @Valid CustomerSaleCreateRequest customerSaleCreateRequest) {
        Client clientFromContext = getClientFromContext();
        SaleCreateRequest saleCreateRequest = new SaleCreateRequest(clientFromContext.getClientCompany().getId(), customerSaleCreateRequest);
        return ApiResponse.CREATED(new SaleResponse(saleService.create(saleCreateRequest, SaleSource.ONLINE)));
    }

    @PutMapping("/sales/{saleId}")
    public ApiResponse<SaleResponse> updateSale(@PathVariable Long saleId,
                                                @RequestBody @Valid CustomerSaleUpdateRequest saleUpdateRequest) {
        Client clientFromContext = getClientFromContext();
        return ApiResponse.OK(new SaleResponse(saleService.update(saleId, saleUpdateRequest, SaleSource.ONLINE)));
    }

    @DeleteMapping("/sales/{saleId}")
    public ApiResponse<Void> deleteSaleById(@PathVariable Long saleId) {
        saleService.deleteById(saleId);
        return ApiResponse.NO_CONTENT();
    }

    @GetMapping("/sales/{saleId}/sale-content-grids")
    public ApiResponse<List<CustomerSaleContentGridResponse>> findSaleContentsBySaleId(@PathVariable Long saleId) {
        Client client = getClientFromContext();
        Long clientCompanyId = client.getClientCompany().getId();
        return ApiResponse.OK(gridService.findCustomerSaleContentGridsByClientCompanyIdAndSaleId(clientCompanyId, saleId));
    }

    private Client getClientFromContext() {
        return clientService.findByUsername(SecurityUtils.getUserName().orElseThrow(() -> new UsernameNotFoundException(SystemMessage.USER_NAME_NOT_FOUND)));
    }

    private ClientCompany getClientCompanyFromContext() {
        Client client = getClientFromContext();
        return clientCompanyService.findById(client.getClientCompany().getId());
    }
}
