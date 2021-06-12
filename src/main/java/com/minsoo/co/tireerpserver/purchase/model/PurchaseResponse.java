package com.minsoo.co.tireerpserver.api.v1.model.dto.purchase;

import com.minsoo.co.tireerpserver.purchase.code.PurchaseStatus;
import com.minsoo.co.tireerpserver.management.model.vendor.VendorResponse;
import com.minsoo.co.tireerpserver.api.v1.model.dto.purchase.content.PurchaseContentSimpleResponse;
import com.minsoo.co.tireerpserver.purchase.entity.Purchase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class PurchaseResponse {

    @Schema(name = "purchase_id", description = "purchase_id", example = "20019")
    private Long purchaseId;

    @Schema(name = "vendor", description = "매입처")
    private VendorResponse vendor;

    @Schema(name = "status", description = "매입 상태", example = "CONFIRMED")
    private PurchaseStatus status;

    @Schema(name = "transaction_date", description = "매입 일자", example = "2021-02-18")
    private LocalDate transactionDate;

    @Schema(name = "contents", description = "매입 항목")
    private List<PurchaseContentSimpleResponse> contents;

    public PurchaseResponse(Purchase purchase) {
        this.purchaseId = purchase.getId();
        this.vendor = VendorResponse.of(purchase.getVendor());
        this.status = purchase.getStatus();
        this.transactionDate = purchase.getTransactionDate();
        this.contents = purchase.getContents().stream().map(PurchaseContentSimpleResponse::of).collect(Collectors.toList());
    }

    public static PurchaseResponse of(Purchase purchase) {
        return new PurchaseResponse(purchase);
    }
}
