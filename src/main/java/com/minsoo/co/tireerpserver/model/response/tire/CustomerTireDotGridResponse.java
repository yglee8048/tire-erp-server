package com.minsoo.co.tireerpserver.model.response.tire;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerTireDotGridResponse {

    private TireDotResponse tireDot;

    private Long price;

    private Integer sumOfOpenedStock;

    public CustomerTireDotGridResponse(TireDotGridResponse tireDotGridResponse) {
        this.tireDot = tireDotGridResponse.getTireDot();

        this.price = tireDotGridResponse.getPrice();

        this.sumOfOpenedStock = tireDotGridResponse.getSumOfOpenedStock();
    }
}
