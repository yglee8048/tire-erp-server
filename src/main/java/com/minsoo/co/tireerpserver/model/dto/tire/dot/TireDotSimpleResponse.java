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

    private Long tireDotId;

    private Long tireId;

    private String dot;

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
