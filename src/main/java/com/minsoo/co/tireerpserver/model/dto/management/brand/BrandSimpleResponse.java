package com.minsoo.co.tireerpserver.model.dto.management.brand;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import static lombok.AccessLevel.PROTECTED;

@Getter
@ToString
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class BrandSimpleResponse {

    @ApiModelProperty(value = "브랜드 ID", example = "2991")
    @JsonProperty("brand_id")
    private Long brandId;

    @ApiModelProperty(value = "브랜드 이름", example = "금호타이어")
    @JsonProperty("name")
    private String name;
}
