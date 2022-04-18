package com.minsoo.co.tireerpserver.api;

import com.minsoo.co.tireerpserver.constant.SaleSource;
import com.minsoo.co.tireerpserver.constant.SaleStatus;
import com.minsoo.co.tireerpserver.model.ApiResponse;
import com.minsoo.co.tireerpserver.model.request.customer.sale.CustomerDeliveryRequest;
import com.minsoo.co.tireerpserver.model.request.customer.sale.CustomerSaleCreateRequest;
import com.minsoo.co.tireerpserver.model.request.customer.sale.CustomerSaleMemoRequest;
import com.minsoo.co.tireerpserver.model.request.customer.sale.CustomerSaleUpdateRequest;
import com.minsoo.co.tireerpserver.model.request.sale.DeliveryRequest;
import com.minsoo.co.tireerpserver.model.request.sale.SaleDateType;
import com.minsoo.co.tireerpserver.model.response.client.ClientCompanyResponse;
import com.minsoo.co.tireerpserver.model.response.client.ClientResponse;
import com.minsoo.co.tireerpserver.model.response.sale.CustomerSaleContentGridResponse;
import com.minsoo.co.tireerpserver.model.response.sale.DeliveryResponse;
import com.minsoo.co.tireerpserver.model.response.sale.SaleMemoResponse;
import com.minsoo.co.tireerpserver.model.response.sale.SaleResponse;
import com.minsoo.co.tireerpserver.model.response.stock.StockGridResponse;
import com.minsoo.co.tireerpserver.model.response.tire.CustomerTireDotGridResponse;
import com.minsoo.co.tireerpserver.model.response.tire.CustomerTireGridResponse;
import com.minsoo.co.tireerpserver.service.customer.CustomerService;
import com.minsoo.co.tireerpserver.service.sale.DeliveryService;
import com.minsoo.co.tireerpserver.service.sale.SaleMemoService;
import com.minsoo.co.tireerpserver.service.sale.SaleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
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
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/customer/api/v1")
@RequiredArgsConstructor
public class CustomerApi {

    private final CustomerService customerService;
    private final SaleService saleService;
    private final DeliveryService deliveryService;
    private final SaleMemoService saleMemoService;

    @GetMapping("/client")
    public ApiResponse<ClientResponse> findClient() {
        return ApiResponse.OK(customerService.findContextUser());
    }

    @GetMapping("/client-company")
    public ApiResponse<ClientCompanyResponse> findClientCompany() {
        return ApiResponse.OK(customerService.findContextUserCompany());
    }

    @GetMapping("/tire-grids")
    public ApiResponse<List<CustomerTireGridResponse>> findCustomerTireGrids() {
        return ApiResponse.OK(customerService.findCustomerTireGrids());
    }

    @GetMapping("/tires/{tireId}/tire-dot-grids")
    public ApiResponse<List<CustomerTireDotGridResponse>> findCustomerTireDotGrids(@PathVariable Long tireId) {
        return ApiResponse.OK(customerService.findTireDotGridsByTireId(tireId));
    }

    @GetMapping("/tire-dots/{tireDotId}/stocks")
    public ApiResponse<List<StockGridResponse>> findStocks(@PathVariable Long tireDotId) {
        return ApiResponse.OK(customerService.findOpenAndHasQuantityStockGridsByTireDotId(tireDotId));
    }

    @GetMapping("/sale-content-grids")
    public ApiResponse<List<CustomerSaleContentGridResponse>> findCustomerSaleGrids(@RequestParam(required = false) SaleStatus status,
                                                                                    @RequestParam(required = false) SaleSource source,
                                                                                    @RequestParam(required = false, name = "sale_date_type") SaleDateType saleDateType,
                                                                                    @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
                                                                                    @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to) {
        return ApiResponse.OK(customerService.findCustomerSaleContentGrids(status, source, saleDateType, from, to));
    }

    @GetMapping("/sales/{saleId}")
    public ApiResponse<SaleResponse> findSaleById(@PathVariable Long saleId) {
        return ApiResponse.OK(saleService.findById(saleId));
    }

    @PostMapping("/sales")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<SaleResponse> createSale(@RequestBody @Valid CustomerSaleCreateRequest customerSaleCreateRequest) {
        return ApiResponse.CREATED(customerService.create(customerSaleCreateRequest));
    }

    @PutMapping("/sales/{saleId}")
    public ApiResponse<SaleResponse> updateSale(@PathVariable Long saleId,
                                                @RequestBody @Valid CustomerSaleUpdateRequest saleUpdateRequest) {
        return ApiResponse.OK(customerService.update(saleId, saleUpdateRequest));
    }

    @DeleteMapping("/sales/{saleId}")
    public ApiResponse<Void> deleteSaleById(@PathVariable Long saleId) {
        customerService.deleteSaleById(saleId);
        return ApiResponse.NO_CONTENT();
    }

    @GetMapping("/sales/{saleId}/sale-content-grids")
    public ApiResponse<List<CustomerSaleContentGridResponse>> findSaleContentsBySaleId(@PathVariable Long saleId) {
        return ApiResponse.OK(customerService.findCustomerSaleContentGridsBySaleId(saleId));
    }

    @GetMapping("/sales/{saleId}/delivery")
    public ApiResponse<DeliveryResponse> findDeliveryBySaleId(@PathVariable Long saleId) {
        return ApiResponse.OK(deliveryService.findBySaleId(saleId));
    }

    @PutMapping("/sales/{saleId}/delivery")
    public ApiResponse<DeliveryResponse> updateDeliveryBySaleId(@PathVariable Long saleId,
                                                                @RequestBody CustomerDeliveryRequest customerDeliveryRequest) {
        return ApiResponse.OK(deliveryService.update(saleId, new DeliveryRequest(customerDeliveryRequest)));
    }

    @GetMapping("/sales/{saleId}/sale-memos")
    public ApiResponse<List<SaleMemoResponse>> findSaleMemosBySaleId(@PathVariable Long saleId) {
        return ApiResponse.OK(saleMemoService.findAllBySale(saleId).stream()
                .filter(saleMemoResponse -> !saleMemoResponse.getLock())
                .collect(Collectors.toList()));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/sales/{saleId}/sale-memos")
    public ApiResponse<SaleMemoResponse> createSaleMemo(@PathVariable Long saleId,
                                                        @RequestBody CustomerSaleMemoRequest customerSaleMemoRequest) {
        return ApiResponse.CREATED(saleMemoService.create(saleId, customerSaleMemoRequest));
    }

    @PutMapping("/sales/{saleId}/sale-memos/{saleMemoId}")
    public ApiResponse<SaleMemoResponse> updateSaleMemo(@PathVariable Long saleId,
                                                        @PathVariable Long saleMemoId,
                                                        @RequestBody CustomerSaleMemoRequest customerSaleMemoRequest) {
        return ApiResponse.OK(saleMemoService.update(saleMemoId, customerSaleMemoRequest));
    }

    @DeleteMapping("/sales/{saleId}/sale-memos/{saleMemoId}")
    public ApiResponse<Void> deleteSaleMemo(@PathVariable Long saleId, @PathVariable Long saleMemoId) {
        saleMemoService.deleteById(saleMemoId);
        return ApiResponse.NO_CONTENT();
    }
}
