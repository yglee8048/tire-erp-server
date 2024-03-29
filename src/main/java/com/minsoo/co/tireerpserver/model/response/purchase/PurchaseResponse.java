package com.minsoo.co.tireerpserver.model.response.purchase;

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
    private LocalDate transactionDate;
    private String description;

    private String createdBy;
    private LocalDateTime createdAt;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedAt;

    public PurchaseResponse(Purchase purchase) {
        this.purchaseId = purchase.getId();
        this.vendorId = purchase.getVendor().getId();
        this.transactionDate = purchase.getTransactionDate();
        this.description = purchase.getDescription();

        this.createdAt = purchase.getCreatedAt();
        this.lastModifiedAt = purchase.getLastModifiedAt();
        this.createdBy = purchase.getCreatedBy();
        this.lastModifiedBy = purchase.getLastModifiedBy();
    }
}
