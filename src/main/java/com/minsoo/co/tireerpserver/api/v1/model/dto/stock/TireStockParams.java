package com.minsoo.co.tireerpserver.api.v1.model.dto.stock;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TireStockParams {

    @Schema(name = "sizes", description = "사이즈(표기형식) 목록")
    private List<String> sizes;

    @Schema(name = "brand_names", description = "제조사 이름 목록")
    private List<String> brandNames;

    @Schema(name = "pattern_names", description = "패턴 이름 목록")
    private List<String> patternNames;

    @Schema(name = "product_ids", description = "타이어 ID 목록")
    private List<String> productIds;
}
