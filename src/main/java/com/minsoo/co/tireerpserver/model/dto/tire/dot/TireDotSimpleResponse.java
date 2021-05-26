package com.minsoo.co.tireerpserver.model.dto.tire.dot;

import com.minsoo.co.tireerpserver.model.entity.entities.tire.TireDot;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class TireDotSimpleResponse {

    @Schema(name = "tire_dot_id", description = "tire_dot_id")
    private Long tireDotId;

    @Schema(name = "tire_id", description = "tire_id")
    private Long tireId;

    @Schema(name = "dot", description = "DOT")
    private String dot;

    @Schema(name = "retail_price", description = "소비자금액")
    private Long retailPrice;

    private TireDotSimpleResponse(TireDot tireDot) {
        this.tireDotId = tireDot.getId();
        this.tireId = tireDot.getTire().getId();
        this.dot = tireDot.getDot();
        this.retailPrice = tireDot.getRetailPrice();
    }

    public static TireDotSimpleResponse of(TireDot tireDot) {
        return new TireDotSimpleResponse(tireDot);
    }
}
