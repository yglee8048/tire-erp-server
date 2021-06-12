package com.minsoo.co.tireerpserver.management.model.brand;

import com.minsoo.co.tireerpserver.management.entity.Brand;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
public class BrandResponse {

    @Schema(name = "brand_id", description = "brand_id", example = "2991")
    private Long brandId;

    @Schema(name = "name", description = "이름", example = "금호타이어")
    private String name;

    @Schema(name = "description", description = "설명", example = "20년 5월부터 계약 시작")
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
