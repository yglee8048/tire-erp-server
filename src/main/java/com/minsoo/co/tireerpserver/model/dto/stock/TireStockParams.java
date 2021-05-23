package com.minsoo.co.tireerpserver.model.dto.stock;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TireStockParams {

    @Schema(description = "사이즈")
    private List<String> sizes;

    @Schema(description = "브랜드 이름")
    private List<String> brandNames;

    @Schema(description = "패턴")
    private List<String> patternNames;

    @Schema(description = "상품 ID")
    private List<String> productIds;
}
