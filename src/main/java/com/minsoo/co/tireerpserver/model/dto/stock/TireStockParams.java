package com.minsoo.co.tireerpserver.model.dto.stock;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TireStockParams {

    @Schema(name = "사이즈")
    private List<String> sizes;

    @Schema(name = "브랜드 이름")
    private List<String> brandNames;

    @Schema(name = "패턴")
    private List<String> patternNames;

    @Schema(name = "상품 ID")
    private List<String> productIds;
}
