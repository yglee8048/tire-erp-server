package com.minsoo.co.tireerpserver.model.stock.response;

import com.minsoo.co.tireerpserver.entity.tire.Tire;
import com.minsoo.co.tireerpserver.entity.tire.TireDot;
import com.minsoo.co.tireerpserver.model.tire.response.TireResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TireStockResponse {

    private TireResponse tireResponse;
    private Long sumOfStock;
    private Long sumOfOpenedStock;
    private Long numberOfActiveDot;
    private Double averageOfPurchasePrice;

    public TireStockResponse(Tire tire, Double averageOfPurchasePrice) {
        this.tireResponse = new TireResponse(tire);
        this.sumOfStock = tire.getTireDots().stream()
                .mapToLong(TireDot::getSumOfStockQuantity)
                .sum();
        this.sumOfOpenedStock = tire.getTireDots().stream()
                .mapToLong(TireDot::getSumOfOpenedStockQuantity)
                .sum();
        this.numberOfActiveDot = tire.getTireDots().stream()
                .filter(TireDot::isActive).count();
        this.averageOfPurchasePrice = averageOfPurchasePrice;
    }
}
