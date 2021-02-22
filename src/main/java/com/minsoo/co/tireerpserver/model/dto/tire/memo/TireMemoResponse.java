package com.minsoo.co.tireerpserver.model.dto.tire.memo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minsoo.co.tireerpserver.model.entity.TireMemo;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class TireMemoResponse {

    @ApiModelProperty(value = "ID", example = "2991")
    @JsonProperty("tire_memo_id")
    private Long tireMemoId;

    @ApiModelProperty(value = "타이어 ID", example = "2991")
    @JsonProperty("tire_id")
    private Long tireId;

    @ApiModelProperty(value = "내용", example = "포르쉐에서 50EA 출고 요청")
    @JsonProperty("memo")
    private String memo;

    @ApiModelProperty(value = "잠금 여부 (true=비공개/false=공개)", example = "true")
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
