package com.minsoo.co.tireerpserver.model.dto.management.brand;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minsoo.co.tireerpserver.model.entity.Brand;
import lombok.Data;

@Data
public class BrandResponse {

    @JsonProperty("brand_id")
    private Long brandId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    public BrandResponse(Brand brand) {
        this.brandId = brand.getId();
        this.name = brand.getName();
        this.description = brand.getDescription();
    }
}
