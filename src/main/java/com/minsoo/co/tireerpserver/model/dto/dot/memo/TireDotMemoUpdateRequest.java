package com.minsoo.co.tireerpserver.model.dto.dot.memo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class TireDotMemoUpdateRequest {

    @NotEmpty
    @JsonProperty("tire_dot_memo_id")
    private Long tireDotMemoId;

    @NotEmpty
    @JsonProperty("tire_dot_id")
    private Long tireDotId;

    @JsonProperty("memo")
    private String memo;

    @JsonProperty("lock")
    private boolean lock;
}