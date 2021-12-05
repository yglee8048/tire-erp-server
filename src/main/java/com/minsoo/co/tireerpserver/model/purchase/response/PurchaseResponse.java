package com.minsoo.co.tireerpserver.model.purchase.response;

import com.minsoo.co.tireerpserver.entity.purchase.Purchase;
import com.minsoo.co.tireerpserver.constant.PurchaseStatus;
import com.minsoo.co.tireerpserver.model.management.response.VendorResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class PurchaseResponse {

    private Long purchaseId;
    private VendorResponse vendor;
    private PurchaseStatus status;
    private LocalDate transactionDate;
    private List<PurchaseContentResponse> contents;

    public PurchaseResponse(Purchase purchase) {
        this.purchaseId = purchase.getId();
        this.vendor = new VendorResponse(purchase.getVendor());
        this.status = purchase.getStatus();
        this.transactionDate = purchase.getTransactionDate();
        this.contents = purchase.getPurchaseContents().stream()
                .map(PurchaseContentResponse::new)
                .collect(Collectors.toList());
    }
}
