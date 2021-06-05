package com.minsoo.co.tireerpserver.api.v1.model.dto.stock;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockRequest {

    @Schema(name = "warehouse_id", description = "warehouse_id")
    @NotNull(message = "warehouse_id 는 필수 값입니다.")
    private Long warehouseId;

    @Schema(name = "nickname", description = "별칭")
    @NotEmpty(message = "별칭은 필수 값입니다.")
    private String nickname;

    @Schema(name = "quantity", description = "수량")
    @NotNull(message = "수량은 필수 값입니다.")
    @Positive(message = "수량은 양수여야 합니다.")
    private Long quantity;

    @Schema(name = "lock", description = "잠금여부")
    @NotNull(message = "잠금여부는 필수 값입니다.")
    private boolean lock;
}
