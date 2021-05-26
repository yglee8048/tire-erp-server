package com.minsoo.co.tireerpserver.model.dto.management.pattern;

import com.minsoo.co.tireerpserver.model.dto.management.brand.BrandResponse;
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
}
