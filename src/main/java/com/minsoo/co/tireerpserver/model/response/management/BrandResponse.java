package com.minsoo.co.tireerpserver.model.response.management;

import com.minsoo.co.tireerpserver.entity.management.Brand;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BrandResponse {

    private Long brandId;
    private String name;
    private String description;

    private String createdBy;
    private LocalDateTime createdAt;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedAt;

    public BrandResponse(Brand brand) {
        this.brandId = brand.getId();
        this.name = brand.getName();
        this.description = brand.getDescription();

        this.createdAt = brand.getCreatedAt();
        this.lastModifiedAt = brand.getLastModifiedAt();
        this.createdBy = brand.getCreatedBy();
        this.lastModifiedBy = brand.getLastModifiedBy();
    }
}
