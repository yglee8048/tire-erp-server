package com.minsoo.co.tireerpserver.model.dto.dot.memo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TireMemoCreateRequest {

    @JsonProperty("memo")
    private String memo;

    @NotNull
    @JsonProperty("lock")
    private boolean lock;
}
