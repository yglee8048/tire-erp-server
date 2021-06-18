package com.minsoo.co.tireerpserver.user.api;

import com.minsoo.co.tireerpserver.shared.model.ApiResponse;
import com.minsoo.co.tireerpserver.user.model.client.ClientRequest;
import com.minsoo.co.tireerpserver.user.model.client.ClientResponse;
import com.minsoo.co.tireerpserver.user.model.client.company.ClientCompanyRequest;
import com.minsoo.co.tireerpserver.user.model.client.company.ClientCompanyResponse;
import com.minsoo.co.tireerpserver.user.service.ClientCompanyService;
import com.minsoo.co.tireerpserver.user.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1")
@RequiredArgsConstructor
public class ClientApi {

    private final ClientCompanyService clientCompanyService;
    private final ClientService clientService;

    @GetMapping(value = "/client-companies")
    @Operation(summary = "고객사 목록 조회")
    public ApiResponse<List<ClientCompanyResponse>> findClientCompanies() {
        return ApiResponse.OK(clientCompanyService.findAll()
                .stream()
                .map(ClientCompanyResponse::of)
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/client-companies/{clientCompanyId}")
    @Operation(summary = "고객사 조회")
    public ApiResponse<ClientCompanyResponse> findClientCompanyById(@PathVariable Long clientCompanyId) {
        return ApiResponse.OK(ClientCompanyResponse.of(clientCompanyService.findById(clientCompanyId)));
    }

    @PostMapping(value = "/client-companies")
    @Operation(summary = "고객사 생성")
    public ResponseEntity<ApiResponse<Void>> createClientCompany(@RequestBody @Valid ClientCompanyRequest clientCompanyRequest) {
        return ApiResponse.CREATED(
                linkTo(methodOn(ClientApi.class).findClientCompanyById(clientCompanyService.create(clientCompanyRequest).getId())).toUri());
    }

    @PutMapping(value = "/client-companies/{clientCompanyId}")
    @Operation(summary = "고객사 수정")
    public ApiResponse<Void> updateClientCompany(@PathVariable Long clientCompanyId, @RequestBody @Valid ClientCompanyRequest clientCompanyRequest) {
        clientCompanyService.update(clientCompanyId, clientCompanyRequest);
        return ApiResponse.OK;
    }

    @DeleteMapping(value = "/client-companies/{clientCompanyId}")
    @Operation(summary = "고객사 삭제")
    public ApiResponse<Void> deleteClientCompany(@PathVariable Long clientCompanyId) {
        clientCompanyService.removeById(clientCompanyId);
        return ApiResponse.OK;
    }

    @GetMapping(value = "/client-companies/{clientCompanyId}/clients")
    @Operation(summary = "고객사 고객 조회")
    public ApiResponse<List<ClientResponse>> findClientsByClientCompanyId(@PathVariable Long clientCompanyId) {
        return ApiResponse.OK(clientService.findAllByClientCompanyId(clientCompanyId)
                .stream()
                .map(ClientResponse::of)
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/client-companies/{clientCompanyId}/clients/{clientId}")
    @Operation(summary = "고객 조회")
    public ApiResponse<ClientResponse> findClientById(@PathVariable Long clientCompanyId, @PathVariable Long clientId) {
        return ApiResponse.OK(ClientResponse.of(clientService.findById(clientId)));
    }

    @PostMapping(value = "/client-companies/{clientCompanyId}/clients")
    @Operation(summary = "고객 생성")
    public ResponseEntity<ApiResponse<Void>> createClient(@PathVariable Long clientCompanyId, @RequestBody @Valid ClientRequest clientRequest) {
        return ApiResponse.CREATED(
                linkTo(methodOn(ClientApi.class).findClientById(clientCompanyId, clientService.create(clientCompanyId, clientRequest).getId())).toUri());
    }

    @PutMapping(value = "/client-companies/{clientCompanyId}/clients/{clientId}")
    @Operation(summary = "고객 수정")
    public ApiResponse<Void> updateClient(@PathVariable Long clientCompanyId, @PathVariable Long clientId, @RequestBody @Valid ClientRequest clientRequest) {
        clientService.update(clientCompanyId, clientId, clientRequest);
        return ApiResponse.OK;
    }

    @DeleteMapping(value = "/client-companies/{clientCompanyId}/clients/{clientId}")
    @Operation(summary = "고객 삭제")
    public ApiResponse<Void> deleteClient(@PathVariable Long clientCompanyId, @PathVariable Long clientId) {
        clientService.removeById(clientId);
        return ApiResponse.OK;
    }
}
