package com.minsoo.co.tireerpserver.model.response.grid;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TireDotGridResponse {

    private Long tireDotId;
    private Long tireId;
    private String dot;
    private Integer sumOfOpenedStock;
    private Integer sumOfStock;
    private Double averageOfPurchasePrice;
}
