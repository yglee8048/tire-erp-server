package com.minsoo.co.tireerpserver.user.api;

import com.minsoo.co.tireerpserver.shared.model.ApiResponse;
import com.minsoo.co.tireerpserver.user.model.client.company.ClientCompanyResponse;
import com.minsoo.co.tireerpserver.user.service.ClientCompanyService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1")
@RequiredArgsConstructor
public class ClientApi {

    private final ClientCompanyService clientCompanyService;

    @GetMapping(value = "/client-companies")
    @Operation(summary = "고객사 목록 조회")
    public ApiResponse<List<ClientCompanyResponse>> findClientCompanies() {
        return ApiResponse.OK(clientCompanyService.findAll()
                .stream()
                .map(ClientCompanyResponse::of)
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/client-companies/{clientCompanyId}")
    @Operation(summary = "고객사 상세 조회")
    public ApiResponse<ClientCompanyResponse> findClientCompanyById(@PathVariable Long clientCompanyId) {
        return ApiResponse.OK(ClientCompanyResponse.of(clientCompanyService.findById(clientCompanyId)));
    }

}
