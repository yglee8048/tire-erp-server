package com.minsoo.co.tireerpserver.model.dto.purchase;

import com.minsoo.co.tireerpserver.model.code.PurchaseStatus;
import com.minsoo.co.tireerpserver.model.dto.management.vendor.VendorResponse;
import com.minsoo.co.tireerpserver.model.entity.purchase.Purchase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class PurchaseSimpleResponse {

    @Schema(name = "purchase_id", description = "purchase_id", example = "20019")
    private Long purchaseId;

    @Schema(name = "vendor", description = "매입처")
    private VendorResponse vendor;

    @Schema(name = "status", description = "매입 상태", example = "CONFIRMED")
    private PurchaseStatus status;

    @Schema(name = "purchase_date", description = "매입 일자", example = "2021-02-18")
    private LocalDate purchaseDate;

    public PurchaseSimpleResponse(Purchase purchase) {
        this.purchaseId = purchase.getId();
        this.vendor = VendorResponse.of(purchase.getVendor());
        this.status = purchase.getStatus();
        this.purchaseDate = purchase.getPurchaseDate();
    }

    public static PurchaseSimpleResponse of(Purchase purchase) {
        return new PurchaseSimpleResponse(purchase);
    }
}
