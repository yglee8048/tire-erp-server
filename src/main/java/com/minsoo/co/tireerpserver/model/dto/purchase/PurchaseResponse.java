package com.minsoo.co.tireerpserver.model.dto.purchase;

import com.minsoo.co.tireerpserver.model.code.PurchaseStatus;
import com.minsoo.co.tireerpserver.model.dto.management.vendor.VendorResponse;
import com.minsoo.co.tireerpserver.model.entity.entities.purchase.Purchase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class PurchaseResponse {

    @Schema(description = "ID", example = "20019")
    private Long purchaseId;

    @Schema(description = "매입처")
    private VendorResponse vendor;

    @Schema(description = "매입 상태", example = "CONFIRMED")
    private PurchaseStatus status;

    @Schema(description = "매입 일자", example = "2021-02-18")
    private LocalDate purchaseDate;

    @Schema(description = "매입 항목")
    private List<PurchaseContentResponse> contents;

    public PurchaseResponse(Purchase purchase) {
        this.purchaseId = purchase.getId();
        this.vendor = VendorResponse.of(purchase.getVendor());
        this.status = purchase.getStatus();
        this.purchaseDate = purchase.getPurchaseDate();
        this.contents = purchase.getContents().stream().map(PurchaseContentResponse::of).collect(Collectors.toList());
    }

    public static PurchaseResponse of(Purchase purchase) {
        return new PurchaseResponse(purchase);
    }
}
