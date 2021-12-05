package com.minsoo.co.tireerpserver.model.management.response;

import com.minsoo.co.tireerpserver.entity.management.Brand;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BrandResponse {

    private Long brandId;
    private String name;
    private String description;

    public BrandResponse(Brand brand) {
        this.brandId = brand.getId();
        this.name = brand.getName();
        this.description = brand.getDescription();
    }
}
