package com.minsoo.co.tireerpserver.model.response.grid.customer;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerTireDotGridResponse {

    private Long tireDotId;
    private Long tireId;
    private String dot;
    private Integer price;
    private Integer sumOfOpenedStock;
}
