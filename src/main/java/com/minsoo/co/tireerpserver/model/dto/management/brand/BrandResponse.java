package com.minsoo.co.tireerpserver.model.dto.management.brand;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minsoo.co.tireerpserver.model.entity.Brand;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static lombok.AccessLevel.PROTECTED;

@Getter
@ToString
@NoArgsConstructor(access = PROTECTED)
public class BrandResponse {

    @JsonProperty("brand_id")
    private Long brandId;

    @JsonProperty("name")
    private String name;

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
