package com.minsoo.co.tireerpserver.model.dto.purchase.content;

import com.minsoo.co.tireerpserver.model.dto.tire.dot.TireDotResponse;
import com.minsoo.co.tireerpserver.model.entity.purchase.PurchaseContent;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PurchaseContentSimpleResponse {

    @Schema(name = "purchase_content_id", description = "purchase_content_id")
    private Long purchaseContentId;

    @Schema(name = "purchase_id", description = "매입 ID")
    private Long purchaseId;

    @Schema(name = "tire_dot", description = "타이어 DOT")
    private TireDotResponse tireDot;

    @Schema(name = "price", description = "매입 가격", example = "450000")
    private Integer price;

    @Schema(name = "quantity", description = "매입 수량", example = "45")
    private Long quantity;

    public PurchaseContentSimpleResponse(PurchaseContent purchaseContent) {
        this.purchaseContentId = purchaseContent.getId();
        this.purchaseId = purchaseContent.getPurchase().getId();
        this.tireDot = TireDotResponse.of(purchaseContent.getTireDot());
        this.price = purchaseContent.getPrice();
        this.quantity = purchaseContent.getQuantity();
    }

    public static PurchaseContentSimpleResponse of(PurchaseContent purchaseContent) {
        return new PurchaseContentSimpleResponse(purchaseContent);
    }
}
