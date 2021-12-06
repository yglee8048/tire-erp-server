package com.minsoo.co.tireerpserver.model.request.sale;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleContentRequest {

    @Schema(name = "tire_dot_id")
    @NotNull(message = "tire_dot_id 는 필수 값입니다.")
    private Long tireDotId;

    @Schema(name = "price", description = "매출 가격")
    @NotNull(message = "매출 가격은 필수 값입니다.")
    private Long price;

    @Schema(name = "quantity", description = "매출 수량")
    @NotNull(message = "매출 수량은 필수 값입니다.")
    private Integer quantity;
}
