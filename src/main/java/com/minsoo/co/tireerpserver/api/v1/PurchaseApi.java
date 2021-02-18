package com.minsoo.co.tireerpserver.api.v1;

import com.minsoo.co.tireerpserver.model.dto.purchase.PurchaseCreateRequest;
import com.minsoo.co.tireerpserver.model.dto.purchase.PurchaseResponse;
import com.minsoo.co.tireerpserver.model.dto.purchase.PurchaseUpdateRequest;
import com.minsoo.co.tireerpserver.model.response.ApiResponse;
import com.minsoo.co.tireerpserver.service.PurchaseService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/purchases")
@RequiredArgsConstructor
public class PurchaseApi {

    private final PurchaseService purchaseService;

    @GetMapping
    @ApiOperation(value = "매입 내역 목록 조회", notes = "매입 내역의 목록을 조회한다.")
    public ApiResponse<List<PurchaseResponse>> findAllPurchases() {
        return ApiResponse.createOK(purchaseService.findAll()
                .stream()
                .map(PurchaseResponse::of)
                .collect(Collectors.toList()));
    }

    @PostMapping
    @ApiOperation(value = "매입 생성", notes = "복수의 매입을 생성한다.")
    public ApiResponse<List<PurchaseResponse>> createPurchases(@RequestBody @Valid PurchaseCreateRequest createRequest) {
        return ApiResponse.createOK(purchaseService.create(createRequest)
                .stream()
                .map(PurchaseResponse::of)
                .collect(Collectors.toList()));
    }

    @PutMapping("/{purchaseId}")
    @ApiOperation(value = "매입 수정", notes = "매입 내용을 수정한다. (확정된 매입은 수정이 불가능하다.)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "purchaseId", value = "매입 ID", example = "201324", required = true)})
    public ApiResponse<PurchaseResponse> updatePurchase(@PathVariable(value = "purchaseId") Long purchaseId,
                                                        @RequestBody @Valid PurchaseUpdateRequest updateRequest) {
        return ApiResponse.createOK(PurchaseResponse.of(purchaseService.update(purchaseId, updateRequest)));
    }

    @PostMapping(value = "/{purchaseId}/confirm")
    @ApiOperation(value = "매입 확정", notes = "매입을 확정한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "purchaseId", value = "매입 ID", example = "201324", required = true),
            @ApiImplicitParam(name = "lock", value = "재고 잠금 여부(true=비공개/false=공개)", example = "true", required = true)})
    public ApiResponse<PurchaseResponse> confirmPurchaseById(@PathVariable(value = "purchaseId") Long purchaseId,
                                                             @RequestParam boolean lock) {
        return ApiResponse.createOK(PurchaseResponse.of(purchaseService.confirm(purchaseId, lock)));
    }

    @DeleteMapping("/{purchaseId}")
    @ApiOperation(value = "매입 삭제", notes = "매입 내용을 삭제한다. (확정된 매입은 삭제가 불가능하다.)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "purchaseId", value = "매입 ID", example = "201324", required = true)})
    public ApiResponse<String> deletePurchase(@PathVariable(value = "purchaseId") Long purchaseId) {
        purchaseService.removeById(purchaseId);
        return ApiResponse.DEFAULT_OK;
    }
}
