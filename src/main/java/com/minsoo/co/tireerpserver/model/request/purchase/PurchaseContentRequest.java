package com.minsoo.co.tireerpserver.model.request.purchase;

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
public class PurchaseContentRequest {

    @Schema(name = "tire_id", description = "tire_id", example = "2", required = true)
    @NotNull(message = "tire_id 는 필수 값입니다.")
    private Long tireId;

    @Schema(name = "dot", description = "타이어 DOT", example = "8859", required = true)
    @NotNull(message = "dot 는 필수 값입니다.")
    private String dot;

    @Schema(name = "price", description = "매입 가격", example = "450000", required = true)
    @NotNull(message = "매입 가격은 필수 값입니다.")
    private Integer price;

    @Schema(name = "quantity", description = "매입 수량", example = "45", required = true)
    @NotNull(message = "매입 수량은 필수 값입니다.")
    private Long quantity;

    @Schema(name = "warehouse_id", example = "2", required = true)
    @NotNull(message = "warehouse_id 는 필수 값입니다.")
    private Long warehouseId;
}
