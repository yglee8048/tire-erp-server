package com.minsoo.co.tireerpserver.model.dto.purchase;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseCreateRequestContent {

    @Schema(name = "타이어 ID", example = "20019", required = true)
    @NotNull(message = "타이어 ID 는 필수 값입니다.")
    private Long tireId;

    @Schema(name = "DOT", example = "1223", required = true)
    @NotEmpty(message = "DOT 는 필수 값입니다.")
    private String dot;

    @Schema(name = "창고 ID", example = "20019", required = true)
    @NotNull(message = "창고 ID 는 필수 값입니다.")
    private Long warehouseId;

    @Schema(name = "매입 가격", example = "450000", required = true)
    @NotNull(message = "매입 가격은 필수 값입니다.")
    @Positive(message = "매입 가격은 양수여야 합니다.")
    private Integer price;

    @Schema(name = "매입 수량", example = "45", required = true)
    @NotNull(message = "매입 수량은 필수 값입니다.")
    @Positive(message = "매입 수량은 양수여야 합니다.")
    private Long quantity;
}
