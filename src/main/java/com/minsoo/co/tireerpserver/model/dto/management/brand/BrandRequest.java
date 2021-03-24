package com.minsoo.co.tireerpserver.model.dto.management.brand;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrandRequest {

    @ApiModelProperty(value = "이름", example = "금호타이어", required = true)
    @NotEmpty(message = "브랜드 이름은 필수 값입니다.")
    private String name;

    @ApiModelProperty(value = "설명", example = "20년 5월부터 계약 시작")
    private String description;
}
