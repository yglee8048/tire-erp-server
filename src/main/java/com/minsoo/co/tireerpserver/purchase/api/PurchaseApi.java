package com.minsoo.co.tireerpserver.purchase.api;

import com.minsoo.co.tireerpserver.purchase.model.PurchaseRequest;
import com.minsoo.co.tireerpserver.purchase.model.PurchaseResponse;
import com.minsoo.co.tireerpserver.purchase.model.content.PurchaseContentGridResponse;
import com.minsoo.co.tireerpserver.purchase.model.content.PurchaseContentResponse;
import com.minsoo.co.tireerpserver.purchase.service.PurchaseContentService;
import com.minsoo.co.tireerpserver.purchase.service.PurchaseService;
import com.minsoo.co.tireerpserver.shared.model.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1")
@RequiredArgsConstructor
public class PurchaseApi {

    private final PurchaseService purchaseService;
    private final PurchaseContentService purchaseContentService;

    // PURCHASE-GRID
    @GetMapping(value = "/purchase-content-grid")
    @Operation(summary = "매입 내역 목록 조회 화면", description = "매출 내역 목록을 보여줄 때 사용한다.(Aggregation)")
    public ApiResponse<List<PurchaseContentGridResponse>> findPurchaseContents(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
                                                                               @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to) {
        return ApiResponse.OK(purchaseService.findAllAsGrid(from, to));
    }

    // PURCHASE
    @GetMapping(value = "/purchases")
    @Operation(summary = "매입 내역 목록 조회", description = "매입 내역의 목록을 조회한다.")
    public ApiResponse<List<PurchaseResponse>> findAllPurchases(@RequestParam(required = false) LocalDate from,
                                                                @RequestParam(required = false) LocalDate to) {
        return ApiResponse.OK(purchaseService.findAllAsResponses(from, to));
    }

    @GetMapping("/purchases/{purchaseId}")
    @Operation(summary = "매입 내역 상세 조회", description = "매입 내역의 상세 정보를 조회한다.")
    public ApiResponse<PurchaseResponse> findPurchaseById(@PathVariable Long purchaseId) {
        return ApiResponse.OK(PurchaseResponse.of(purchaseService.findById(purchaseId)));
    }

    @PostMapping(value = "/purchases")
    @Operation(summary = "매입 생성", description = "매입을 생성한다.")
    public ResponseEntity<ApiResponse<Void>> createPurchases(@RequestBody @Valid PurchaseRequest createRequest) {
        return ApiResponse.CREATED(
                linkTo(methodOn(PurchaseApi.class).findPurchaseById(purchaseService.create(createRequest).getId())).toUri());
    }

    @PutMapping("/purchases/{purchaseId}")
    @Operation(summary = "매입 수정", description = "매입 내용을 수정한다. (확정된 매입은 수정이 불가능하다.)")
    public ApiResponse<Void> updatePurchase(@PathVariable Long purchaseId,
                                            @RequestBody @Valid PurchaseRequest updateRequest) {
        purchaseService.update(purchaseId, updateRequest);
        return ApiResponse.NO_CONTENT;
    }

    @PostMapping(value = "/purchases/{purchaseId}/confirm")
    @Operation(summary = "매입 확정", description = "매입을 확정한다. 매입을 확정한 시점에 매입 내용이 재고에 반영된다.")
    public ApiResponse<Void> confirmPurchaseById(@PathVariable Long purchaseId) {
        purchaseService.confirm(purchaseId);
        return ApiResponse.NO_CONTENT;
    }

    @DeleteMapping("/purchases/{purchaseId}")
    @Operation(summary = "매입 삭제", description = "매입 내용을 삭제한다.")
    public ApiResponse<Void> deletePurchase(@PathVariable Long purchaseId) {
        purchaseService.removeById(purchaseId);
        return ApiResponse.NO_CONTENT;
    }

    // PURCHASE-CONTENT
    @GetMapping(value = "/purchases/{purchaseId}/purchase-contents")
    @Operation(summary = "매입 항목 목록 조회")
    public ApiResponse<List<PurchaseContentResponse>> findPurchaseContentsByPurchaseId(@PathVariable Long purchaseId) {
        return ApiResponse.OK(purchaseContentService.findAllByPurchaseId(purchaseId)
                .stream()
                .map(PurchaseContentResponse::of)
                .collect(Collectors.toList()));
    }
}
