package com.minsoo.co.tireerpserver.model.response.grid;

import com.minsoo.co.tireerpserver.entity.tire.TireDot;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TireDotGridResponse {

    private Long tireDotId;
    private Long tireId;
    private String dot;
    private Long sumOfOpenedStock;
    private Long sumOfStock;
    private Double averageOfPurchasePrice;

    public TireDotGridResponse(TireDot tireDot) {
        this.tireDotId = tireDot.getId();
        this.tireId = tireDot.getTire().getId();
        this.dot = tireDot.getDot();
        this.sumOfOpenedStock = tireDot.getSumOfOpenedStockQuantity();
        this.sumOfStock = tireDot.getSumOfStockQuantity();
        this.averageOfPurchasePrice = tireDot.getAveragePurchasePrice();
    }
}
