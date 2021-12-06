package com.minsoo.co.tireerpserver.model.response.grid;

import com.minsoo.co.tireerpserver.entity.tire.Tire;
import com.minsoo.co.tireerpserver.model.TireStandardDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TireGridResponse {

    private TireStandardDTO tireInfo;
    private Long sumOfOpenedStock;
    private Long sumOfStock;
    private Long theNumberOfActiveDots;
    private Double averageOfPurchasePrice;

    public TireGridResponse(Tire tire) {
        this.tireInfo = new TireStandardDTO(tire);
        this.sumOfStock = tire.getSumOfStock();
        this.sumOfOpenedStock = tire.getSumOfOpenedStock();
        this.theNumberOfActiveDots = tire.getTheNumberOfActiveDots();
        this.averageOfPurchasePrice = tire.getAveragePurchasePrice();
    }
}
