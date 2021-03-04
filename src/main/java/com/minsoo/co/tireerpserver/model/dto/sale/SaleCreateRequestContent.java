package com.minsoo.co.tireerpserver.model.dto.sale;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleCreateRequestContent {

    @ApiModelProperty(value = "재고 ID", example = "20019", required = true)
    @NotNull(message = "재고 ID 는 필수 값입니다.")
    @JsonProperty("stock_id")
    private Long stockId;

    @ApiModelProperty(value = "매출 가격", example = "450000", required = true)
    @NotNull(message = "매출 가격은 필수 값입니다.")
    @Positive(message = "매출 가격은 양수여야 합니다.")
    @JsonProperty("price")
    private Integer price;

    @ApiModelProperty(value = "매출 수량", example = "45", required = true)
    @NotNull(message = "매출 수량은 필수 값입니다.")
    @Positive(message = "매출 수량은 양수여야 합니다.")
    @JsonProperty("quantity")
    private Long quantity;
}
