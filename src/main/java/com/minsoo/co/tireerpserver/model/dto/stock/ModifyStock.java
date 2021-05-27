package com.minsoo.co.tireerpserver.model.dto.stock;

import com.minsoo.co.tireerpserver.model.entity.entities.management.Warehouse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModifyStock {

    private Warehouse warehouse;

    private String nickname;

    private Long quantity;

    private boolean lock;

    public static ModifyStock of(Warehouse warehouse, StockRequest stockRequest) {
        return new ModifyStock(warehouse, stockRequest.getNickname(), stockRequest.getQuantity(), stockRequest.isLock());
    }

    public static ModifyStock of(Long stockId, Warehouse warehouse, String nickname, Long quantity, boolean lock) {
        return new ModifyStock(warehouse, nickname, quantity, lock);
    }
}
