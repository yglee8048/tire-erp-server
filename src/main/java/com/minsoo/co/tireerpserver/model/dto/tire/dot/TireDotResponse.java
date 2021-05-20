package com.minsoo.co.tireerpserver.model.dto.tire.dot;

import com.minsoo.co.tireerpserver.model.dto.tire.tire.TireResponse;
import com.minsoo.co.tireerpserver.model.entity.entities.tire.TireDot;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class TireDotResponse {

    private Long tireDotId;

    private TireResponse tire;

    private String dot;

    private TireDotResponse(TireDot tireDot) {
        this.tireDotId = tireDot.getId();
        this.tire = TireResponse.of(tireDot.getTire());
        this.dot = tireDot.getDot();
    }

    public static TireDotResponse of(TireDot tireDot) {
        return new TireDotResponse(tireDot);
    }
}
