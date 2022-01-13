package com.minsoo.co.tireerpserver.model.response.tire;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerTireDotGridResponse {

    private Long tireDotId;
    private Long tireId;

    private String dot;

    private Long price;

    private Integer sumOfOpenedStock;

    public CustomerTireDotGridResponse(TireDotGridResponse tireDotGridResponse) {
        this.tireDotId = tireDotGridResponse.getTireDotId();
        this.tireId = tireDotGridResponse.getTireId();
        this.dot = tireDotGridResponse.getDot();
        this.price = tireDotGridResponse.getPrice();
        this.sumOfOpenedStock = tireDotGridResponse.getSumOfOpenedStock();
    }
}
