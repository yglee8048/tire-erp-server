package com.minsoo.co.tireerpserver.model.dto.dot;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TireDotCreateRequest {

    @JsonProperty("tire_id")
    private Long tireId;

    @JsonProperty("dot")
    private String dot;
}
