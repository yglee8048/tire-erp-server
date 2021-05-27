package com.minsoo.co.tireerpserver.model.dto.purchase;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePurchaseContentRequest {

    @Schema(name = "tire_dot_id", description = "tire_dot_id", example = "20019", required = true)
    @NotNull(message = "tire_dot_id 는 필수 값입니다.")
    private Long tireDotId;

    @Schema(name = "price", description = "매입 가격", example = "450000", required = true)
    @NotNull(message = "매입 가격은 필수 값입니다.")
    @Positive(message = "매입 가격은 양수여야 합니다.")
    private int price;

    @Schema(name = "quantity", description = "매입 수량", example = "45", required = true)
    @NotNull(message = "매입 수량은 필수 값입니다.")
    @Positive(message = "매입 수량은 양수여야 합니다.")
    private long quantity;
}
