package com.minsoo.co.tireerpserver.model.dto.tire.memo;

import com.minsoo.co.tireerpserver.model.entity.entities.tire.TireMemo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class TireMemoResponse {

    @Schema(name = "타이어 메모 ID", example = "2991")
    private Long tireMemoId;

    @Schema(name = "타이어 ID", example = "2991")
    private Long tireId;

    @Schema(name = "내용", example = "포르쉐에서 50EA 출고 요청")
    private String memo;

    @Schema(name = "잠금 여부 (true=비공개/false=공개)", example = "true")
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
