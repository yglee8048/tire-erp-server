package com.minsoo.co.tireerpserver.model.response.tire;

import com.minsoo.co.tireerpserver.entity.tire.TireMemo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TireMemoResponse {

    private Long tireMemoId;
    private Long tireId;
    private String memo;
    private Boolean lock;

    private String createdBy;
    private LocalDateTime createdAt;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedAt;

    public TireMemoResponse(TireMemo tireMemo) {
        this.tireMemoId = tireMemo.getId();
        this.tireId = tireMemo.getTire().getId();
        this.memo = tireMemo.getMemo();
        this.lock = tireMemo.getLock();

        this.createdAt = tireMemo.getCreatedAt();
        this.lastModifiedAt = tireMemo.getLastModifiedAt();
    }
}
