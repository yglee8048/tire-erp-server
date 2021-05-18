package com.minsoo.co.tireerpserver.model.dto.management.brand;

import com.minsoo.co.tireerpserver.model.entity.entities.management.Brand;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class BrandSimpleResponse {

    @Schema(name = "브랜드 ID", example = "2991")
    private Long brandId;

    @Schema(name = "브랜드 이름", example = "금호타이어")
    private String name;

    private BrandSimpleResponse(Brand brand) {
        this.brandId = brand.getId();
        this.name = brand.getName();
    }

    public static BrandSimpleResponse of(Brand brand) {
        return new BrandSimpleResponse(brand);
    }
}
