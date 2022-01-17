package com.minsoo.co.tireerpserver.model.response.sale;

import com.minsoo.co.tireerpserver.entity.sale.SaleMemo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class SaleMemoResponse {

    private Long saleMemoId;
    private Long saleId;
    private String memo;
    private Boolean admin;
    private Boolean lock;

    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
    private String createdBy;
    private String lastModifiedBy;

    public SaleMemoResponse(SaleMemo saleMemo) {
        this.saleMemoId = saleMemo.getId();
        this.saleId = saleMemo.getSale().getId();
        this.memo = saleMemo.getMemo();
        this.admin = saleMemo.getAdmin();
        this.lock = saleMemo.getLock();

        this.createdAt = saleMemo.getCreatedAt();
        this.lastModifiedAt = saleMemo.getLastModifiedAt();
        this.createdBy = saleMemo.getCreatedBy();
        this.lastModifiedBy = saleMemo.getLastModifiedBy();
    }
}
