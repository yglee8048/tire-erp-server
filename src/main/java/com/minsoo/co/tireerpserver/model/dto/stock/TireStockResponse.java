package com.minsoo.co.tireerpserver.model.dto.stock;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minsoo.co.tireerpserver.model.dto.tire.TireResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class TireStockResponse {

    @ApiModelProperty(value = "타이어")
    @JsonProperty("tire")
    private TireResponse tire;

    @ApiModelProperty(value = "매입 평균가", example = "288500")
    @JsonProperty("average_of_purchase_price")
    private Double averageOfPurchasePrice;

    @ApiModelProperty(value = "수량", example = "207")
    @JsonProperty("sum_of_stock")
    private Long sumOfStock;

    @ApiModelProperty(value = "DOT 개수", example = "5")
    @JsonProperty("number_of_dot")
    private Long numberOfDot;
}
