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

    private Long stockId;

    private Warehouse warehouse;

    private String nickname;

    private Long quantity;

    private Boolean lock;

    public static ModifyStock of(Warehouse warehouse, ModifyStockRequest modifyStockRequest) {
        return new ModifyStock(modifyStockRequest.getStockId(), warehouse, modifyStockRequest.getNickname(), modifyStockRequest.getQuantity(), modifyStockRequest.getLock());
    }
}
