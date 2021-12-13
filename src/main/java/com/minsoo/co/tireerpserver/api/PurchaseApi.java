package com.minsoo.co.tireerpserver.api;

import com.minsoo.co.tireerpserver.model.ApiResponse;
import com.minsoo.co.tireerpserver.model.request.purchase.PurchaseRequest;
import com.minsoo.co.tireerpserver.model.response.grid.PurchaseContentGridResponse;
import com.minsoo.co.tireerpserver.model.response.purchase.PurchaseResponse;
import com.minsoo.co.tireerpserver.service.purchase.PurchaseContentService;
import com.minsoo.co.tireerpserver.service.purchase.PurchaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PurchaseApi {

    private final PurchaseService purchaseService;
    private final PurchaseContentService purchaseContentService;

    @GetMapping("/purchase-content-grids")
    public ApiResponse<List<PurchaseContentGridResponse>> findAllPurchaseContents(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
                                                                                  @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to) {
        return ApiResponse.OK(purchaseContentService.findAll().stream()
                .filter(purchaseContent -> purchaseContent.getPurchase().getTransactionDate().isAfter(from))
                .filter(purchaseContent -> purchaseContent.getPurchase().getTransactionDate().isBefore(to))
                .map(PurchaseContentGridResponse::new)
                .collect(Collectors.toList()));
    }

    @GetMapping("/purchases/{purchaseId}/purchase-content-grids")
    public ApiResponse<List<PurchaseContentGridResponse>> findPurchaseContentsByPurchaseId(@PathVariable Long purchaseId) {
        return ApiResponse.OK(purchaseContentService.findByPurchase(purchaseId).stream()
                .map(PurchaseContentGridResponse::new)
                .collect(Collectors.toList()));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/purchases")
    public ApiResponse<PurchaseResponse> createPurchase(@RequestBody @Valid PurchaseRequest purchaseRequest) {
        return ApiResponse.CREATED(new PurchaseResponse(purchaseService.create(purchaseRequest)));
    }

    @GetMapping("/purchases/{purchaseId}")
    public ApiResponse<PurchaseResponse> findPurchaseById(@PathVariable Long purchaseId) {
        return ApiResponse.OK(new PurchaseResponse(purchaseService.findById(purchaseId)));
    }

    @PutMapping("/purchases/{purchaseId}")
    public ApiResponse<PurchaseResponse> updatePurchase(@PathVariable Long purchaseId,
                                                        @RequestBody @Valid PurchaseRequest purchaseRequest) {
        return ApiResponse.OK(new PurchaseResponse(purchaseService.update(purchaseId, purchaseRequest)));
    }

    @DeleteMapping("/purchases/{purchaseId}")
    public ApiResponse<Void> deletePurchase(@PathVariable Long purchaseId) {
        purchaseService.deleteById(purchaseId);
        return ApiResponse.NO_CONTENT();
    }

    @PostMapping("/purchases/{purchaseId}/confirm")
    public ApiResponse<PurchaseResponse> confirmPurchase(@PathVariable Long purchaseId) {
        return ApiResponse.OK(new PurchaseResponse(purchaseService.confirm(purchaseId)));
    }
}
