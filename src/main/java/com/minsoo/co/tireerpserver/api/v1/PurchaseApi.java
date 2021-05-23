package com.minsoo.co.tireerpserver.api.v1;

import com.minsoo.co.tireerpserver.model.dto.purchase.CreatePurchaseRequest;
import com.minsoo.co.tireerpserver.model.dto.purchase.PurchaseConfirmRequest;
import com.minsoo.co.tireerpserver.model.dto.purchase.PurchaseResponse;
import com.minsoo.co.tireerpserver.model.dto.purchase.UpdatePurchaseRequest;
import com.minsoo.co.tireerpserver.model.response.ApiResponse;
import com.minsoo.co.tireerpserver.service.purchase.PurchaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/purchases")
@RequiredArgsConstructor
public class PurchaseApi {

    private final PurchaseService purchaseService;

    @GetMapping
    @Operation(summary = "매입 내역 목록 조회", description = "매입 내역의 목록을 조회한다.")
    public ApiResponse<List<PurchaseResponse>> findAllPurchases() {
        return ApiResponse.OK(purchaseService.findAll()
                .stream()
                .map(PurchaseResponse::of)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{purchaseId}")
    @Operation(summary = "매입 내역 상세 조회", description = "매입 내역의 상세 정보를 조회한다.")
    @Parameters({@Parameter(name = "purchaseId", description = "매입 ID", example = "201324", required = true)})
    public ApiResponse<PurchaseResponse> findPurchaseById(@PathVariable Long purchaseId) {
        return ApiResponse.OK(PurchaseResponse.of(purchaseService.findById(purchaseId)));
    }

    @PostMapping
    @Operation(summary = "매입 생성", description = "복수의 매입을 생성한다.")
    public ResponseEntity<ApiResponse<Object>> createPurchases(@RequestBody @Valid CreatePurchaseRequest createRequest) {
        return ApiResponse.CREATED(
                linkTo(methodOn(PurchaseApi.class, findPurchaseById(purchaseService.create(createRequest).getId()))).toUri());
    }

    @PutMapping("/{purchaseId}")
    @Operation(summary = "매입 수정", description = "매입 내용을 수정한다. (확정된 매입은 수정이 불가능하다.)")
    @Parameters({@Parameter(name = "purchaseId", description = "매입 ID", example = "201324", required = true)})
    public ApiResponse<Object> updatePurchase(@PathVariable Long purchaseId,
                                              @RequestBody @Valid UpdatePurchaseRequest updateRequest) {
        purchaseService.update(purchaseId, updateRequest);
        return ApiResponse.OK;
    }

    @PostMapping(value = "/{purchaseId}/confirm")
    @Operation(summary = "매입 확정", description = "매입을 확정한다. 매입을 확정한 시점에 매입 내용이 재고에 반영된다.")
    public ApiResponse<Object> confirmPurchaseById(@PathVariable Long purchaseId,
                                                   List<PurchaseConfirmRequest> confirmRequests) {
        purchaseService.confirm(purchaseId, confirmRequests);
        return ApiResponse.OK;
    }

    @DeleteMapping("/{purchaseId}")
    @Operation(summary = "매입 삭제", description = "매입 내용을 삭제한다. (확정된 매입은 삭제가 불가능하다.)")
    @Parameters({@Parameter(name = "purchaseId", description = "매입 ID", example = "201324", required = true)})
    public ApiResponse<Object> deletePurchase(@PathVariable Long purchaseId) {
        purchaseService.removeById(purchaseId);
        return ApiResponse.OK;
    }
}
