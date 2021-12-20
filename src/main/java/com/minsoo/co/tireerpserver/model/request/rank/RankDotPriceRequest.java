package com.minsoo.co.tireerpserver.model.request.rank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
public class RankDotPriceRequest {

    @Schema(name = "price", description = "가격")
    @Positive(message = "가격은 양수여야 합니다.")
    @NotNull(message = "가격은 필수 값입니다.")
    private Integer price;
}
