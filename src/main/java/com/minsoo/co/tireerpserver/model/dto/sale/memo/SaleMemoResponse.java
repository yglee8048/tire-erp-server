package com.minsoo.co.tireerpserver.model.dto.sale.memo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class SaleMemoResponse {

    @ApiModelProperty(value = "매출 메모 ID", example = "2991")
    @JsonProperty("sale_memo_id")
    private Long saleMemoId;

    @ApiModelProperty(value = "매출 ID", example = "2991")
    @JsonProperty("sale_id")
    private Long saleId;

    @ApiModelProperty(value = "내용", example = "3월 5일까지 긴급 출고 요망.")
    @JsonProperty("memo")
    private String memo;

    @ApiModelProperty(value = "잠금 여부 (true=비공개/false=공개)", example = "false")
    @JsonProperty("lock")
    private boolean lock;
}
