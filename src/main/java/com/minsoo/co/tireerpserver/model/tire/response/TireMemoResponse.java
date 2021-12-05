package com.minsoo.co.tireerpserver.model.tire.response;

import com.minsoo.co.tireerpserver.entity.tire.TireMemo;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TireMemoResponse {

    private Long id;
    private TireResponse tire;
    private String memo;
    private Boolean lock;

    public TireMemoResponse(TireMemo tireMemo) {
        this.id = tireMemo.getId();
        this.tire = new TireResponse(tireMemo.getTire());
        this.memo = tireMemo.getMemo();
        this.lock = tireMemo.getLock();
    }
}
