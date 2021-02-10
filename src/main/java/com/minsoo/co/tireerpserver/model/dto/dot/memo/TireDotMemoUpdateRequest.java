package com.minsoo.co.tireerpserver.model.dto.dot.memo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TireDotMemoUpdateRequest {

    @NotNull
    @JsonProperty("tire_dot_memo_id")
    private Long tireDotMemoId;

    @NotNull
    @JsonProperty("tire_dot_id")
    private Long tireDotId;

    @JsonProperty("memo")
    private String memo;

    @NotNull
    @JsonProperty("lock")
    private boolean lock;
}
