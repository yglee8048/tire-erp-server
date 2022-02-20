package com.minsoo.co.tireerpserver.model.request.rank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
public class RankDotPriceRequest {

    @Schema(name = "rank_id")
    @NotNull(message = "rank_id 는 필수 값입니다.")
    private Long rankId;

    @Schema(name = "discount_rate", description = "할인율")
    @Positive(message = "할인율은 양수여야 합니다.")
    @NotNull(message = "할인율은 필수 값입니다.")
    private Float discountRate;
}
