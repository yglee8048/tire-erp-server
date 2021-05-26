package com.minsoo.co.tireerpserver.model.dto.tire.tire;

import com.minsoo.co.tireerpserver.model.dto.management.pattern.PatternSimpleResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TireSimpleResponse {

    @Schema(name = "tire_id", description = "tire_id")
    private Long tireId;

    @Schema(name = "on_sale", description = "판매상태")
    private Boolean onSale;

    @Schema(name = "product_id", description = "타이어 ID")
    private String productId;

    @Schema(name = "pattern", description = "패턴 정보")
    private PatternSimpleResponse pattern;

    @Schema(name = "size", description = "표기형식")
    private String size;

    @Schema(name = "load_index", description = "하중지수")
    private Integer loadIndex;

    @Schema(name = "speed_index", description = "속도지수")
    private String speedIndex;
}
