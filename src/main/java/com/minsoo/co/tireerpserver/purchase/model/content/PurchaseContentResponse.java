package com.minsoo.co.tireerpserver.purchase.model.content;

import com.minsoo.co.tireerpserver.purchase.model.PurchaseSimpleResponse;
import com.minsoo.co.tireerpserver.tire.model.dot.TireDotResponse;
import com.minsoo.co.tireerpserver.purchase.entity.PurchaseContent;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PurchaseContentResponse {

    @Schema(name = "purchase_content_id", description = "purchase_content_id")
    private Long purchaseContentId;

    @Schema(name = "purchase", description = "매입 정보")
    private PurchaseSimpleResponse purchase;

    @Schema(name = "tire_dot", description = "타이어 DOT")
    private TireDotResponse tireDot;

    @Schema(name = "price", description = "매입 가격", example = "450000")
    private Integer price;

    @Schema(name = "quantity", description = "매입 수량", example = "45")
    private Long quantity;

    public PurchaseContentResponse(PurchaseContent purchaseContent) {
        this.purchaseContentId = purchaseContent.getId();
        this.purchase = PurchaseSimpleResponse.of(purchaseContent.getPurchase());
        this.tireDot = TireDotResponse.of(purchaseContent.getTireDot());
        this.price = purchaseContent.getPrice();
        this.quantity = purchaseContent.getQuantity();
    }

    public static PurchaseContentResponse of(PurchaseContent purchaseContent) {
        return new PurchaseContentResponse(purchaseContent);
    }
}
