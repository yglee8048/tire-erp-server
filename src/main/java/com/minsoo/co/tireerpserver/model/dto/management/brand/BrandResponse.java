package com.minsoo.co.tireerpserver.model.dto.management.brand;

import com.minsoo.co.tireerpserver.model.entity.Brand;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class BrandResponse {

    @Schema(name = "ID", example = "2991")
    private Long brandId;

    @Schema(name = "이름", example = "금호타이어")
    private String name;

    @Schema(name = "설명", example = "20년 5월부터 계약 시작")
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
