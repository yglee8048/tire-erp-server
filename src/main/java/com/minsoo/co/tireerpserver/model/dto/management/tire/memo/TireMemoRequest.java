package com.minsoo.co.tireerpserver.model.dto.management.tire.memo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TireMemoRequest {

    @ApiModelProperty(value = "내용", example = "포르쉐에서 50EA 출고 요청")
    @JsonProperty("memo")
    private String memo;

    @ApiModelProperty(value = "잠금 여부 (true=비공개/false=공개)", example = "true")
    @NotNull(message = "잠금 여부는 필수 값입니다.")
    @JsonProperty("lock")
    private boolean lock;
}