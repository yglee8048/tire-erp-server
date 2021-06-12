package com.minsoo.co.tireerpserver.management.model.pattern;

import com.minsoo.co.tireerpserver.management.model.brand.BrandResponse;
import com.minsoo.co.tireerpserver.management.entity.Pattern;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PatternSimpleResponse {

    @Schema(name = "brand", description = "브랜드 정보")
    private BrandResponse brand;

    @Schema(name = "name", description = "패턴 이름")
    private String name;

    public PatternSimpleResponse(Pattern pattern) {
        this.brand = BrandResponse.of(pattern.getBrand());
        this.name = pattern.getName();
    }

    public static PatternSimpleResponse of(Pattern pattern) {
        return new PatternSimpleResponse(pattern);
    }
}
