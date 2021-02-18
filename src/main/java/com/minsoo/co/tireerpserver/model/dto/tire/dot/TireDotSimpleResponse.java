package com.minsoo.co.tireerpserver.model.dto.tire.dot;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minsoo.co.tireerpserver.model.entity.TireDot;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class TireDotSimpleResponse {

    @ApiModelProperty(value = "타이어 DOT ID", example = "21")
    @JsonProperty("tire_dot_id")
    private Long tireDotId;

    @ApiModelProperty(value = "타이어 DOT", example = "4014")
    @JsonProperty("dot")
    private String dot;

    private TireDotSimpleResponse(TireDot tireDot) {
        this.tireDotId = tireDot.getId();
        this.dot = tireDot.getDot();
    }

    public static TireDotSimpleResponse of(TireDot tireDot) {
        return new TireDotSimpleResponse(tireDot);
    }
}
