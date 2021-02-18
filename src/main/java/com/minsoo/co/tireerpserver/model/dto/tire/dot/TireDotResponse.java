package com.minsoo.co.tireerpserver.model.dto.tire.dot;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minsoo.co.tireerpserver.model.entity.TireDot;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TireDotResponse {

    @ApiModelProperty(value = "타이어 DOT ID", example = "21")
    @JsonProperty("tire_dot_id")
    private Long tireDotId;

    @ApiModelProperty(value = "타이어 ID", example = "21990")
    @JsonProperty("tire_id")
    private Long tireId;

    @ApiModelProperty(value = "타이어 DOT", example = "4014")
    @JsonProperty("dot")
    private String dot;

    private TireDotResponse(TireDot tireDot) {
        this.tireDotId = tireDot.getId();
        this.tireId = tireDot.getTire().getId();
        this.dot = tireDot.getDot();
    }

    public static TireDotResponse of(TireDot tireDot) {
        return new TireDotResponse(tireDot);
    }
}
