package com.minsoo.co.tireerpserver.tire.model.dot;

import com.minsoo.co.tireerpserver.tire.entity.TireDot;
import com.minsoo.co.tireerpserver.tire.model.TireResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TireDotResponse {

    @Schema(name = "tire_dot_id", description = "tire_dot_id")
    private Long tireDotId;

    @Schema(name = "tire", description = "타이어")
    private TireResponse tire;

    @Schema(name = "dot", description = "DOT")
    private String dot;

    @Schema(name = "total_quantity", description = "전체 재고")
    private Long totalQuantity;

    private TireDotResponse(TireDot tireDot) {
        this.tireDotId = tireDot.getId();
        this.tire = TireResponse.of(tireDot.getTire());
        this.dot = tireDot.getDot();
        this.totalQuantity = tireDot.getSumOfQuantity();
    }

    public static TireDotResponse of(TireDot tireDot) {
        return new TireDotResponse(tireDot);
    }
}
