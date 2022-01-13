package com.minsoo.co.tireerpserver.api;

import com.minsoo.co.tireerpserver.model.ApiResponse;
import com.minsoo.co.tireerpserver.model.request.client.ClientCompanyRequest;
import com.minsoo.co.tireerpserver.model.request.client.ClientRequest;
import com.minsoo.co.tireerpserver.model.response.client.ClientCompanyResponse;
import com.minsoo.co.tireerpserver.model.response.client.ClientResponse;
import com.minsoo.co.tireerpserver.service.client.ClientCompanyService;
import com.minsoo.co.tireerpserver.service.client.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ClientApi {

    private final ClientCompanyService clientCompanyService;
    private final ClientService clientService;

    @GetMapping("/client-companies")
    public ApiResponse<List<ClientCompanyResponse>> findAllClientCompanies() {
        return ApiResponse.OK(clientCompanyService.findAll());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/client-companies")
    public ApiResponse<ClientCompanyResponse> createClientCompany(@RequestBody @Valid ClientCompanyRequest clientCompanyRequest) {
        return ApiResponse.CREATED(clientCompanyService.create(clientCompanyRequest));
    }

    @GetMapping("/client-companies/{clientCompanyId}")
    public ApiResponse<ClientCompanyResponse> findClientCompanyById(@PathVariable Long clientCompanyId) {
        return ApiResponse.OK(clientCompanyService.findById(clientCompanyId));
    }

    @PutMapping("/client-companies/{clientCompanyId}")
    public ApiResponse<ClientCompanyResponse> updateClientCompany(@PathVariable Long clientCompanyId,
                                                                  @RequestBody @Valid ClientCompanyRequest clientCompanyRequest) {
        return ApiResponse.OK(clientCompanyService.update(clientCompanyId, clientCompanyRequest));
    }

    @DeleteMapping("/client-companies/{clientCompanyId}")
    public ApiResponse<Void> deleteClientCompanyById(@PathVariable Long clientCompanyId) {
        clientCompanyService.deleteById(clientCompanyId);
        return ApiResponse.NO_CONTENT();
    }

    @GetMapping("/client-companies/{clientCompanyId}/clients")
    public ApiResponse<List<ClientResponse>> findAllClientsByClientCompany(@PathVariable Long clientCompanyId) {
        return ApiResponse.OK(clientService.findAllByClientCompany(clientCompanyId));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/client-companies/{clientCompanyId}/clients")
    public ApiResponse<ClientResponse> createClient(@PathVariable Long clientCompanyId,
                                                    @RequestBody @Valid ClientRequest clientRequest) {
        return ApiResponse.CREATED(clientService.create(clientCompanyId, clientRequest));
    }

    @GetMapping("/client-companies/{clientCompanyId}/clients/{clientId}")
    public ApiResponse<ClientResponse> findClientById(@PathVariable Long clientCompanyId,
                                                      @PathVariable Long clientId) {
        return ApiResponse.OK(clientService.findById(clientId));
    }

    @PutMapping("/client-companies/{clientCompanyId}/clients/{clientId}")
    public ApiResponse<ClientResponse> updateClient(@PathVariable Long clientCompanyId,
                                                    @PathVariable Long clientId,
                                                    @RequestBody @Valid ClientRequest clientRequest) {
        return ApiResponse.OK(clientService.update(clientId, clientRequest));
    }

    @DeleteMapping("/client-companies/{clientCompanyId}/clients/{clientId}")
    public ApiResponse<Void> deleteClientById(@PathVariable Long clientCompanyId,
                                              @PathVariable Long clientId) {
        clientService.deleteById(clientId);
        return ApiResponse.NO_CONTENT();
    }
}
