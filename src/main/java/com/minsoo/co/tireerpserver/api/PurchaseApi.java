package com.minsoo.co.tireerpserver.api;

import com.minsoo.co.tireerpserver.model.ApiResponse;
import com.minsoo.co.tireerpserver.model.purchase.request.PurchaseRequest;
import com.minsoo.co.tireerpserver.model.purchase.response.PurchaseResponse;
import com.minsoo.co.tireerpserver.service.purchase.PurchaseService;
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
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PurchaseApi {

    private final PurchaseService purchaseService;

    @GetMapping("/purchases")
    public ApiResponse<List<PurchaseResponse>> findAllPurchases() {
        return ApiResponse.OK(purchaseService.findAll()
                .stream()
                .map(PurchaseResponse::new)
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
