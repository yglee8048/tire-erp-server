package com.minsoo.co.tireerpserver.model.dto.dot.memo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TireDotMemoResponse {

    @JsonProperty("tire_dot_memo_id")
    private Long tireDotMemoId;

    @JsonProperty("tire_dot_id")
    private Long tireDotId;

    @JsonProperty("memo")
    private String memo;

    @JsonProperty("open")
    private boolean open;
}
