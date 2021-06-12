package com.minsoo.co.tireerpserver.sale.model.memo;

import com.minsoo.co.tireerpserver.sale.entity.SaleMemo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleMemoResponse {

    @Schema(description = "매출 메모 ID", example = "2991")
    private Long saleMemoId;

    @Schema(description = "매출 ID", example = "2991")
    private Long saleId;

    @Schema(description = "내용", example = "3월 5일까지 긴급 출고 요망.")
    private String memo;

    @Schema(description = "잠금 여부 (true=비공개/false=공개)", example = "false")
    private Boolean lock;

    public static SaleMemoResponse of(SaleMemo saleMemo) {
        return SaleMemoResponse.builder()
                .saleMemoId(saleMemo.getId())
                .saleId(saleMemo.getSale().getId())
                .memo(saleMemo.getMemo())
                .lock(saleMemo.getLock())
                .build();
    }
}
