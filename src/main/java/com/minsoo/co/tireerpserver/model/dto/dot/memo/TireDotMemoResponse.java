package com.minsoo.co.tireerpserver.model.dto.dot.memo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minsoo.co.tireerpserver.model.entity.TireDotMemo;
import lombok.Data;

@Data
public class TireDotMemoResponse {

    @JsonProperty("tire_dot_memo_id")
    private Long tireDotMemoId;

    @JsonProperty("tire_dot_id")
    private Long tireDotId;

    @JsonProperty("memo")
    private String memo;

    @JsonProperty("lock")
    private boolean lock;

    public TireDotMemoResponse(TireDotMemo tireDotMemo) {
        this.tireDotMemoId = tireDotMemo.getId();
        this.tireDotId = tireDotMemo.getTireDot().getId();
        this.memo = tireDotMemo.getMemo();
        this.lock = tireDotMemo.isLock();
    }
}
