package com.minsoo.co.tireerpserver.model.dto.management.tire.dot;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minsoo.co.tireerpserver.model.dto.management.tire.TireResponse;
import lombok.Data;

@Data
public class TireDotResponse {

    @JsonProperty("tire_dot_id")
    private Long tireDotId;

    @JsonProperty("tire_id")
    private Long tireId;

    @JsonProperty("dot")
    private String dot;
}
