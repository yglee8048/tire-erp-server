package com.minsoo.co.tireerpserver.model.dto.tire.dot;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minsoo.co.tireerpserver.model.dto.tire.TireResponse;
import com.minsoo.co.tireerpserver.model.entity.TireDot;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import static lombok.AccessLevel.PROTECTED;

@Getter
@ToString
@NoArgsConstructor(access = PROTECTED)
public class TireDotResponse {

    @ApiModelProperty(value = "ID", example = "21")
    @JsonProperty("tire_dot_id")
    private Long tireDotId;

    @ApiModelProperty(value = "타이어")
    @JsonProperty("tire")
    private TireResponse tire;

    @ApiModelProperty(value = "타이어 DOT", example = "4014")
    @JsonProperty("dot")
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
