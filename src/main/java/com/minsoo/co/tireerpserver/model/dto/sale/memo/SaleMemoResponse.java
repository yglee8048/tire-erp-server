package com.minsoo.co.tireerpserver.model.dto.sale.memo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class SaleMemoResponse {

    @JsonProperty("sale_memo_id")
    private Long saleMemoId;

    @JsonProperty("sale_id")
    private Long saleId;

    @JsonProperty("memo")
    private String memo;

    @JsonProperty("lock")
    private boolean lock;
}
