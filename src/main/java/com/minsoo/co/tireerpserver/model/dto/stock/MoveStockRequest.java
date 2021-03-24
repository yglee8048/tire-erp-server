package com.minsoo.co.tireerpserver.model.dto.stock;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MoveStockRequest {

    @ApiModelProperty(value = "이동할 창고 ID", example = "2091", required = true)
    @NotNull(message = "이동할 창고의 ID는 필수 값입니다.")
    private Long toWarehouseId;

    @ApiModelProperty(value = "이동할 수량", example = "20", required = true)
    @NotNull(message = "이동할 수량은 필수 값입니다.")
    @Positive(message = "이동할 수량은 양수여야 합니다.")
    private Long quantity;
}
