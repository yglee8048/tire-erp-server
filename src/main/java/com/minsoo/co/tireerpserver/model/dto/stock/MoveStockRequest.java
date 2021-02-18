package com.minsoo.co.tireerpserver.model.dto.stock;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import static lombok.AccessLevel.PROTECTED;

@Getter
@ToString
@NoArgsConstructor(access = PROTECTED)
public class MoveStockRequest {

    @ApiModelProperty(value = "이동할 창고 ID", example = "2091")
    @NotNull(message = "이동할 창고의 ID는 필수 값입니다.")
    @JsonProperty("to_warehouse_id")
    private Long toWarehouseId;

    @ApiModelProperty(value = "이동할 수량", example = "20")
    @NotNull(message = "이동할 수량은 필수 값입니다.")
    @Positive(message = "이동할 수량은 양수여야 합니다.")
    @JsonProperty("quantity")
    private Long quantity;
}
