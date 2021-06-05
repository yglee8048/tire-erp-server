package com.minsoo.co.tireerpserver.api.v1.model.dto.sale.memo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class SaleMemoResponse {

    @Schema(description = "매출 메모 ID", example = "2991")
    private Long saleMemoId;

    @Schema(description = "매출 ID", example = "2991")
    private Long saleId;

    @Schema(description = "내용", example = "3월 5일까지 긴급 출고 요망.")
    private String memo;

    @Schema(description = "잠금 여부 (true=비공개/false=공개)", example = "false")
    private boolean lock;
}
