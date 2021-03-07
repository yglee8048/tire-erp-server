package com.minsoo.co.tireerpserver.model.dto.sale.content;

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
public class SaleContentUpdateRequest {

    @ApiModelProperty(value = "타이어 DOT ID", example = "2991", required = true)
    @NotNull(message = "타이어 DOT ID 는 필수 값입니다.")
    @JsonProperty("tire_dot_id")
    private Long tireDotId;

    @ApiModelProperty(value = "매출 수량", example = "20", required = true)
    @NotNull(message = "매출 수량은 필수 값입니다.")
    @Positive(message = "매출 수량은 양수여야 합니다.")
    @JsonProperty("quantity")
    private Long quantity;

    @ApiModelProperty(value = "매출 금액", example = "240000", required = true)
    @NotNull(message = "매출 금액은 필수 값입니다.")
    @Positive(message = "매출 금액은 양수여야 합니다.")
    @JsonProperty("price")
    private Integer price;
}
