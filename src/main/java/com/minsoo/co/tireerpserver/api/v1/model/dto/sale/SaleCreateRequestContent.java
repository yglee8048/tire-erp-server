package com.minsoo.co.tireerpserver.api.v1.model.dto.sale;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleCreateRequestContent {

    @Schema(description = "재고 ID", example = "20019", required = true)
    @NotNull(message = "재고 ID 는 필수 값입니다.")
    private Long stockId;

    @Schema(description = "매출 가격", example = "450000", required = true)
    @NotNull(message = "매출 가격은 필수 값입니다.")
    @Positive(message = "매출 가격은 양수여야 합니다.")
    private Integer price;

    @Schema(description = "매출 수량", example = "45", required = true)
    @NotNull(message = "매출 수량은 필수 값입니다.")
    @Positive(message = "매출 수량은 양수여야 합니다.")
    private Long quantity;
}
