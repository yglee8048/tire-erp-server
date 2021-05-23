package com.minsoo.co.tireerpserver.model.dto.purchase;

import com.minsoo.co.tireerpserver.model.dto.tire.dot.TireDotResponse;
import com.minsoo.co.tireerpserver.model.entity.entities.purchase.PurchaseContent;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PurchaseContentResponse {

    @Schema(description = "매입 항목 ID")
    private Long purchaseContentId;

    @Schema(description = "매입 ID")
    private Long purchaseId;

    @Schema(description = "타이어 DOT")
    private TireDotResponse tireDot;

    @Schema(description = "매입 가격", example = "450000")
    private Integer price;

    @Schema(description = "매입 수량", example = "45")
    private Long quantity;

    public PurchaseContentResponse(PurchaseContent purchaseContent) {
        this.purchaseContentId = purchaseContent.getId();
        this.purchaseId = purchaseContent.getPurchase().getId();
        this.tireDot = TireDotResponse.of(purchaseContent.getTireDot());
        this.price = purchaseContent.getPrice();
        this.quantity = purchaseContent.getQuantity();
    }

    public static PurchaseContentResponse of(PurchaseContent purchaseContent) {
        return new PurchaseContentResponse(purchaseContent);
    }
}
