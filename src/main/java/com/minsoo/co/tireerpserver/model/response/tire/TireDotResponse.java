package com.minsoo.co.tireerpserver.model.response.tire;

import com.minsoo.co.tireerpserver.entity.tire.TireDot;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TireDotResponse {

    private Long tireDotId;
    private Long tireId;
    private String dot;

    public TireDotResponse(TireDot tireDot) {
        this.tireDotId = tireDot.getId();
        this.tireId = tireDot.getTire().getId();
        this.dot = tireDot.getDot();
    }
}
