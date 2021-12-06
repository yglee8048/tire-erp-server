package com.minsoo.co.tireerpserver.model.response.sale;

import com.minsoo.co.tireerpserver.entity.sale.SaleMemo;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SaleMemoResponse {

    private Long saleMemoId;
    private Long saleId;
    private String memo;
    private boolean lock;

    public SaleMemoResponse(SaleMemo saleMemo) {
        this.saleMemoId = saleMemo.getId();
        this.saleId = saleMemo.getSale().getId();
        this.memo = saleMemo.getMemo();
        this.lock = saleMemo.getLock();
    }
}
