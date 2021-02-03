package com.minsoo.co.tireerpserver.model.dto.dot.memo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TireDotMemoCreateRequest {

    @JsonProperty("memo")
    private String memo;

    @JsonProperty("lock")
    private boolean lock;
}
