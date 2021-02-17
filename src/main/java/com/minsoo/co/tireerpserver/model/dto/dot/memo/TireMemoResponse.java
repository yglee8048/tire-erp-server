package com.minsoo.co.tireerpserver.model.dto.dot.memo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minsoo.co.tireerpserver.model.entity.TireMemo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static lombok.AccessLevel.PROTECTED;

@Getter
@ToString
@NoArgsConstructor(access = PROTECTED)
public class TireMemoResponse {

    @JsonProperty("tire_memo_id")
    private Long tireMemoId;

    @JsonProperty("tire_id")
    private Long tireId;

    @JsonProperty("memo")
    private String memo;

    @JsonProperty("lock")
    private boolean lock;

    private TireMemoResponse(TireMemo tireMemo) {
        this.tireMemoId = tireMemo.getId();
        this.tireId = tireMemo.getTire().getId();
        this.memo = tireMemo.getMemo();
        this.lock = tireMemo.isLock();
    }

    public static TireMemoResponse of(TireMemo tireMemo) {
        return new TireMemoResponse(tireMemo);
    }
}
