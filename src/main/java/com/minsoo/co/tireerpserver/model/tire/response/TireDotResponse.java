package com.minsoo.co.tireerpserver.model.tire.response;

import com.minsoo.co.tireerpserver.entity.tire.TireDot;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TireDotResponse {

    private Long id;
    private TireResponse tire;
    private String dot;
    private Integer price;

    public TireDotResponse(TireDot tireDot) {
        this.id = tireDot.getId();
        this.tire = new TireResponse(tireDot.getTire());
        this.dot = tireDot.getDot();
        this.price = tireDot.getPrice();
    }
}
