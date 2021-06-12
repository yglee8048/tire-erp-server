package com.minsoo.co.tireerpserver.sale.model.content;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleContentRequest {

    @Schema(name = "tire_dot_id")
    @NotNull(message = "tire_dot_id 는 필수 값입니다.")
    private Long tireDotId;

    @Schema(name = "price", description = "매출 가격")
    @NotNull(message = "매출 가격은 필수 값입니다.")
    @Positive(message = "매출 가격은 양수여야 합니다.")
    private Integer price;

    @Schema(name = "quantity", description = "매출 수량")
    @NotNull(message = "매출 수량은 필수 값입니다.")
    @Positive(message = "매출 수량은 양수여야 합니다.")
    private Long quantity;
}
