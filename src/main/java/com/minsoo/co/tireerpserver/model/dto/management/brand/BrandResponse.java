package com.minsoo.co.tireerpserver.model.dto.management.brand;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minsoo.co.tireerpserver.model.entity.Brand;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static lombok.AccessLevel.PROTECTED;

@Getter
@ToString
@NoArgsConstructor(access = PROTECTED)
public class BrandResponse {

    @ApiModelProperty(value = "ID", example = "2991")
    @JsonProperty("brand_id")
    private Long brandId;

    @ApiModelProperty(value = "이름", example = "금호타이어")
    @JsonProperty("name")
    private String name;

    @ApiModelProperty(value = "설명", example = "20년 5월부터 매입 시작")
    @JsonProperty("description")
    private String description;

    private BrandResponse(Brand brand) {
        this.brandId = brand.getId();
        this.name = brand.getName();
        this.description = brand.getDescription();
    }

    public static BrandResponse of(Brand brand) {
        return new BrandResponse(brand);
    }
}
