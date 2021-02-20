package com.minsoo.co.tireerpserver.model.dto.management.brand;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minsoo.co.tireerpserver.model.entity.Brand;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import static lombok.AccessLevel.PROTECTED;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = PROTECTED)
public class BrandSimpleResponse {

    @ApiModelProperty(value = "브랜드 ID", example = "2991")
    @JsonProperty("brand_id")
    private Long brandId;

    @ApiModelProperty(value = "브랜드 이름", example = "금호타이어")
    @JsonProperty("name")
    private String name;

    private BrandSimpleResponse(Brand brand) {
        this.brandId = brand.getId();
        this.name = brand.getName();
    }

    public static BrandSimpleResponse of(Brand brand) {
        return new BrandSimpleResponse(brand);
    }
}
