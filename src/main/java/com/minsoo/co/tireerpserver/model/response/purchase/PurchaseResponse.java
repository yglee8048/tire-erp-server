package com.minsoo.co.tireerpserver.model.response.purchase;

import com.minsoo.co.tireerpserver.constant.PurchaseStatus;
import com.minsoo.co.tireerpserver.entity.purchase.Purchase;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PurchaseResponse {

    private Long purchaseId;
    private Long vendorId;
    private PurchaseStatus status;
    private LocalDate transactionDate;

    private String createdBy;
    private LocalDateTime createdAt;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedAt;

    public PurchaseResponse(Purchase purchase) {
        this.purchaseId = purchase.getId();
        this.vendorId = purchase.getVendor().getId();
        this.status = purchase.getStatus();
        this.transactionDate = purchase.getTransactionDate();

        this.createdAt = purchase.getCreatedAt();
        this.lastModifiedAt = purchase.getLastModifiedAt();
    }
}
