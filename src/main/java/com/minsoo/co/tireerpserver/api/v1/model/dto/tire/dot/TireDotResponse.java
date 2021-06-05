package com.minsoo.co.tireerpserver.api.v1.model.dto.tire.dot;

import com.minsoo.co.tireerpserver.api.v1.model.dto.tire.TireSimpleResponse;
import com.minsoo.co.tireerpserver.services.tire.entity.TireDot;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
public class TireDotResponse {

    @Schema(name = "tire_dot_id", description = "tire_dot_id")
    private Long tireDotId;

    @Schema(name = "tire", description = "타이어")
    private TireSimpleResponse tire;

    @Schema(name = "dot", description = "DOT")
    private String dot;

    @Schema(name = "retail_price", description = "소비자금액")
    private Integer retailPrice;

    @Schema(name = "total_quantity", description = "전체 재고")
    private Long totalQuantity;

    private TireDotResponse(TireDot tireDot) {
        this.tireDotId = tireDot.getId();
        this.tire = TireSimpleResponse.of(tireDot.getTire());
        this.dot = tireDot.getDot();
        this.retailPrice = tireDot.getRetailPrice();
        this.totalQuantity = tireDot.getSumOfQuantity();
    }

    public static TireDotResponse of(TireDot tireDot) {
        return new TireDotResponse(tireDot);
    }
}
