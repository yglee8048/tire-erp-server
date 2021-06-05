package com.minsoo.co.tireerpserver.api.v1.model.dto.tire.memo;

import com.minsoo.co.tireerpserver.services.tire.entity.TireMemo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
public class TireMemoResponse {

    @Schema(name = "tire_memo_id", description = "tire_memo_id", example = "2991")
    private Long tireMemoId;

    @Schema(name = "tire_id", description = "tire_id", example = "2991")
    private Long tireId;

    @Schema(name = "memo", description = "내용", example = "포르쉐에서 50EA 출고 요청")
    private String memo;

    @Schema(name = "lock", description = "잠금 여부 (true=비공개/false=공개)", example = "true")
    private Boolean lock;

    private TireMemoResponse(TireMemo tireMemo) {
        this.tireMemoId = tireMemo.getId();
        this.tireId = tireMemo.getTire().getId();
        this.memo = tireMemo.getMemo();
        this.lock = tireMemo.getLock();
    }

    public static TireMemoResponse of(TireMemo tireMemo) {
        return new TireMemoResponse(tireMemo);
    }
}
