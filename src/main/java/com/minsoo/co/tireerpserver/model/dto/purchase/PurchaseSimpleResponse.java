package com.minsoo.co.tireerpserver.model.dto.purchase;

import com.minsoo.co.tireerpserver.model.code.PurchaseStatus;
import com.minsoo.co.tireerpserver.model.dto.tire.dot.TireDotSimpleResponse;
import com.minsoo.co.tireerpserver.model.entity.Purchase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class PurchaseSimpleResponse {

    @Schema(name = "매입 ID", example = "20019")
    private Long purchaseId;

    @Schema(name = "매입처 ID")
    private Long vendorId;

    @Schema(name = "타이어 DOT")
    private TireDotSimpleResponse tireDot;

    @Schema(name = "창고 ID")
    private Long warehouseId;

    @Schema(name = "매입 가격", example = "450000")
    private Integer price;

    @Schema(name = "매입 수량", example = "45")
    private Long quantity;

    @Schema(name = "매입 상태", example = "CONFIRMED")
    private PurchaseStatus status;

    @Schema(name = "매입 일자", example = "2021-02-18")
    private LocalDate purchaseDate;

    private PurchaseSimpleResponse(Purchase purchase) {
        this.purchaseId = purchase.getId();
        this.vendorId = purchase.getVendor().getId();
        this.tireDot = TireDotSimpleResponse.of(purchase.getTireDot());
        this.warehouseId = purchase.getWarehouse().getId();
        this.price = purchase.getPrice();
        this.quantity = purchase.getQuantity();
        this.status = purchase.getStatus();
        this.purchaseDate = purchase.getPurchaseDate();
    }

    public static PurchaseSimpleResponse of(Purchase purchase) {
        return new PurchaseSimpleResponse(purchase);
    }
}
