package com.minsoo.co.tireerpserver.api.v1.model.dto.sale.content;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleContentUpdateRequest {

    @Schema(description = "타이어 DOT ID", example = "2991", required = true)
    @NotNull(message = "타이어 DOT ID 는 필수 값입니다.")
    private Long tireDotId;

    @Schema(description = "매출 수량", example = "20", required = true)
    @NotNull(message = "매출 수량은 필수 값입니다.")
    @Positive(message = "매출 수량은 양수여야 합니다.")
    private Long quantity;

    @Schema(description = "매출 금액", example = "240000", required = true)
    @NotNull(message = "매출 금액은 필수 값입니다.")
    @Positive(message = "매출 금액은 양수여야 합니다.")
    private Integer price;
}
