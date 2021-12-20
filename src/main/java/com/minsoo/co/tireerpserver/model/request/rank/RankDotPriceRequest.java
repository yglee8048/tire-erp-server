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

    @Schema(name = "tire_dot_id")
    @NotNull(message = "tire_dot_id 는 필수 값입니다.")
    private Long tireDotId;

    @Schema(name = "price", description = "가격")
    @Positive(message = "가격은 양수여야 합니다.")
    @NotNull(message = "가격은 필수 값입니다.")
    private Integer price;
}
