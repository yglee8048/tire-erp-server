package com.minsoo.co.tireerpserver.model.dto.tire.dot;

import com.minsoo.co.tireerpserver.model.dto.tire.TireResponse;
import com.minsoo.co.tireerpserver.model.entity.TireDot;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class TireDotResponse {

    @Schema(name = "ID", example = "21")
    private Long tireDotId;

    @Schema(name = "타이어")
    private TireResponse tire;

    @Schema(name = "타이어 DOT", example = "4014")
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
