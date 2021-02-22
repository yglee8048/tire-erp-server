package com.minsoo.co.tireerpserver.model.dto.stock;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockUpdateLockRequest {

    @ApiModelProperty(value = "잠금 여부(true=비공개/false=공개)", example = "true")
    @NotNull(message = "잠금 여부는 필수 값입니다.")
    @JsonProperty("lock")
    private boolean lock;
}
