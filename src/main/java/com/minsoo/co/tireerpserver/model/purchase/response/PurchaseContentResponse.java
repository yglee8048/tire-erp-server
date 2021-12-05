package com.minsoo.co.tireerpserver.model.purchase.response;

import com.minsoo.co.tireerpserver.entity.purchase.PurchaseContent;
import com.minsoo.co.tireerpserver.model.management.response.WarehouseResponse;
import com.minsoo.co.tireerpserver.model.tire.response.TireDotResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PurchaseContentResponse {

    private Long purchaseContentId;
    private TireDotResponse tireDot;
    private Integer price;
    private Long quantity;
    private WarehouseResponse warehouse;

    public PurchaseContentResponse(PurchaseContent purchaseContent) {
        this.purchaseContentId = purchaseContent.getId();
        this.tireDot = new TireDotResponse(purchaseContent.getTireDot());
        this.price = purchaseContent.getPrice();
        this.quantity = purchaseContent.getQuantity();
        this.warehouse = new WarehouseResponse(purchaseContent.getWarehouse());
    }
}
