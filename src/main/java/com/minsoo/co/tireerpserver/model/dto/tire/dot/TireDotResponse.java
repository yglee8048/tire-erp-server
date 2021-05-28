package com.minsoo.co.tireerpserver.model.dto.tire.dot;

import com.minsoo.co.tireerpserver.model.dto.tire.TireSimpleResponse;
import com.minsoo.co.tireerpserver.model.entity.entities.tire.TireDot;
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
    private int retailPrice;

    private TireDotResponse(TireDot tireDot) {
        this.tireDotId = tireDot.getId();
        this.tire = TireSimpleResponse.of(tireDot.getTire());
        this.dot = tireDot.getDot();
        this.retailPrice = tireDot.getRetailPrice();
    }

    public static TireDotResponse of(TireDot tireDot) {
        return new TireDotResponse(tireDot);
    }
}
