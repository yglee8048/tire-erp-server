package com.minsoo.co.tireerpserver.model.dto.dot;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TireDotCreateRequest {

    @NotNull
    @JsonProperty("tire_id")
    private Long tireId;

    @NotNull
    @JsonProperty("dot")
    private String dot;
}
