package com.minsoo.co.tireerpserver.model.request.customer.sale;

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
public class CustomerSaleContentRequest {

    @Schema(name = "tire_dot_id")
    @NotNull(message = "tire_dot_id 는 필수 값 입니다.")
    private Long tireDotId;

    @Schema(name = "stock_id")
    @NotNull(message = "stock_id 는 필수 값 입니다.")
    private Long stockId;

    @Schema(name = "quantity", description = "매출 수량")
    @NotNull(message = "매출 수량은 필수 값 입니다.")
    private Integer quantity;
}
