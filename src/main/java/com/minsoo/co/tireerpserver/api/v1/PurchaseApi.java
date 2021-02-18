package com.minsoo.co.tireerpserver.api.v1;

import com.minsoo.co.tireerpserver.model.dto.purchase.PurchaseRequest;
import com.minsoo.co.tireerpserver.model.dto.purchase.PurchaseResponse;
import com.minsoo.co.tireerpserver.model.dto.response.ApiResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/purchases")
@RequiredArgsConstructor
public class PurchaseApi {

    @PostMapping
    private ApiResponseDTO<PurchaseResponse> createPurchase(@RequestBody @Valid List<PurchaseRequest> createRequest) {
        return ApiResponseDTO.createOK(null);
    }
}
