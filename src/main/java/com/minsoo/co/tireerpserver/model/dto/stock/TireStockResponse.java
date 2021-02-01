package com.minsoo.co.tireerpserver.model.dto.stock;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minsoo.co.tireerpserver.model.dto.management.tire.TireResponse;
import lombok.Data;

@Data
public class TireStockResponse {

    @JsonProperty("tire")
    private TireResponse tire;

    @JsonProperty("average_of_purchase_price")
    private Long averageOfPurchasePrice;

    @JsonProperty("sum_of_stock")
    private Integer sumOfStock;

    @JsonProperty("number_of_dot")
    private Integer numberOfDot;
}
