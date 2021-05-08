package com.minsoo.co.tireerpserver.api.v1;

import com.minsoo.co.tireerpserver.model.dto.purchase.PurchaseCreateRequest;
import com.minsoo.co.tireerpserver.model.dto.purchase.PurchaseResponse;
import com.minsoo.co.tireerpserver.model.dto.purchase.PurchaseSimpleResponse;
import com.minsoo.co.tireerpserver.model.dto.purchase.PurchaseUpdateRequest;
import com.minsoo.co.tireerpserver.model.response.ApiResponse;
import com.minsoo.co.tireerpserver.service.PurchaseService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/purchases")
@RequiredArgsConstructor
public class PurchaseApi {

    private final PurchaseService purchaseService;

    @GetMapping
    @Tag(name = "매입 내역 목록 조회", description = "매입 내역의 목록을 조회한다.")
    public ApiResponse<List<PurchaseResponse>> findAllPurchases() {
        return ApiResponse.createOK(purchaseService.findAll());
    }

    @PostMapping
    @Tag(name = "매입 생성", description = "복수의 매입을 생성한다.")
    public ApiResponse<List<PurchaseSimpleResponse>> createPurchases(@RequestBody @Valid PurchaseCreateRequest createRequest) {
        return ApiResponse.createOK(purchaseService.create(createRequest));
    }

    @PutMapping("/{purchaseId}")
    @Tag(name = "매입 수정", description = "매입 내용을 수정한다. (확정된 매입은 수정이 불가능하다.)")
    @Parameters({@Parameter(name = "purchaseId", description = "매입 ID", example = "201324", required = true)})
    public ApiResponse<PurchaseSimpleResponse> updatePurchase(@PathVariable(value = "purchaseId") Long purchaseId,
                                                              @RequestBody @Valid PurchaseUpdateRequest updateRequest) {
        return ApiResponse.createOK(purchaseService.update(purchaseId, updateRequest));
    }

    @PostMapping(value = "/{purchaseId}/confirm")
    @Tag(name = "매입 확정", description = "매입을 확정한다. 매입을 확정한 시점에 매입 내용이 재고에 반영된다.")
    @Parameters({@Parameter(name = "purchaseId", description = "매입 ID", example = "201324", required = true)})
    public ApiResponse<PurchaseSimpleResponse> confirmPurchaseById(@PathVariable(value = "purchaseId") Long purchaseId) {
        return ApiResponse.createOK(purchaseService.confirm(purchaseId));
    }

    @DeleteMapping("/{purchaseId}")
    @Tag(name = "매입 삭제", description = "매입 내용을 삭제한다. (확정된 매입은 삭제가 불가능하다.)")
    @Parameters({@Parameter(name = "purchaseId", description = "매입 ID", example = "201324", required = true)})
    public ApiResponse<String> deletePurchase(@PathVariable(value = "purchaseId") Long purchaseId) {
        purchaseService.removeById(purchaseId);
        return ApiResponse.DEFAULT_OK;
    }
}
