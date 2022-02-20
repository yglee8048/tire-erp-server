package com.minsoo.co.tireerpserver.api;

import com.minsoo.co.tireerpserver.model.ApiResponse;
import com.minsoo.co.tireerpserver.model.request.purchase.PurchaseRequest;
import com.minsoo.co.tireerpserver.model.response.purchase.PurchaseContentGridResponse;
import com.minsoo.co.tireerpserver.model.response.purchase.PurchaseResponse;
import com.minsoo.co.tireerpserver.service.purchase.PurchaseContentService;
import com.minsoo.co.tireerpserver.service.purchase.PurchaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PurchaseApi {

    private final PurchaseService purchaseService;
    private final PurchaseContentService purchaseContentService;

    @GetMapping("/purchase-content-grids")
    public ApiResponse<List<PurchaseContentGridResponse>> findAllPurchaseContents(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
                                                                                  @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to) {
        return ApiResponse.OK(purchaseContentService.findAllPurchaseContentGrids(from, to));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/purchases")
    public ApiResponse<PurchaseResponse> createPurchase(@RequestBody @Valid PurchaseRequest purchaseRequest) {
        return ApiResponse.CREATED(purchaseService.create(purchaseRequest));
    }

    @GetMapping("/purchases/{purchaseId}")
    public ApiResponse<PurchaseResponse> findPurchaseById(@PathVariable Long purchaseId) {
        return ApiResponse.OK(purchaseService.findById(purchaseId));
    }

    @PutMapping("/purchases/{purchaseId}")
    public ApiResponse<PurchaseResponse> updatePurchase(@PathVariable Long purchaseId,
                                                        @RequestBody @Valid PurchaseRequest purchaseRequest) {
        return ApiResponse.OK(purchaseService.update(purchaseId, purchaseRequest));
    }

    @DeleteMapping("/purchases/{purchaseId}")
    public ApiResponse<Void> deletePurchase(@PathVariable Long purchaseId) {
        purchaseService.deleteById(purchaseId);
        return ApiResponse.NO_CONTENT();
    }

    @GetMapping("/purchases/{purchaseId}/purchase-content-grids")
    public ApiResponse<List<PurchaseContentGridResponse>> findPurchaseContentsByPurchaseId(@PathVariable Long purchaseId) {
        return ApiResponse.OK(purchaseContentService.findPurchaseContentGridsByPurchaseId(purchaseId));
    }
}
