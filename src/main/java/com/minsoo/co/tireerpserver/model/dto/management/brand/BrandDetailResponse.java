package com.minsoo.co.tireerpserver.model.dto.management.brand;

import com.minsoo.co.tireerpserver.model.dto.management.pattern.PatternResponse;
import com.minsoo.co.tireerpserver.model.entity.entities.management.Brand;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class BrandDetailResponse {

    @Schema(name = "ID", example = "2991")
    private Long brandId;

    @Schema(name = "이름", example = "금호타이어")
    private String name;

    @Schema(name = "설명", example = "20년 5월부터 계약 시작")
    private String description;

    private List<PatternResponse> patterns;

    public BrandDetailResponse(Brand brand) {
        this.brandId = brand.getId();
        this.name = brand.getName();
        this.description = brand.getDescription();
        this.patterns = brand.getPatterns().stream().map(PatternResponse::of).collect(Collectors.toList());
    }

    public static BrandDetailResponse of(Brand brand) {
        return new BrandDetailResponse(brand);
    }
}
