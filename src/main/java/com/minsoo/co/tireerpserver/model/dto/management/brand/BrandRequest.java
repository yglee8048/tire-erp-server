package com.minsoo.co.tireerpserver.model.dto.management.brand;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class BrandRequest {

    @ApiModelProperty(value = "이름", example = "금호타이어")
    @NotNull(message = "제조사 이름은 필수 값입니다.")
    @JsonProperty("name")
    private String name;

    @ApiModelProperty(value = "설명", example = "20년 5월부터 매입 시작")
    @JsonProperty("description")
    private String description;
}
