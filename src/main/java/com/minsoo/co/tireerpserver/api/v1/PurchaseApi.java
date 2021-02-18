package com.minsoo.co.tireerpserver.api.v1;

import com.minsoo.co.tireerpserver.model.dto.purchase.PurchaseRequest;
import com.minsoo.co.tireerpserver.model.dto.purchase.PurchaseResponse;
import com.minsoo.co.tireerpserver.model.response.ApiResponse;
import com.minsoo.co.tireerpserver.service.PurchaseService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/purchases")
@RequiredArgsConstructor
public class PurchaseApi {

    private final PurchaseService purchaseService;

    @PostMapping
    @ApiOperation(value = "매입 생성", notes = "복수의 매입을 생성한다.")
    public ApiResponse<List<PurchaseResponse>> createPurchases(@RequestBody @Valid PurchaseRequest createRequest) {
        return ApiResponse.createOK(purchaseService.create(createRequest)
                .stream()
                .map(PurchaseResponse::of)
                .collect(Collectors.toList()));
    }
}
