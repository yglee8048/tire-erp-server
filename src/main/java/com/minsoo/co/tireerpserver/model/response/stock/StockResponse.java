package com.minsoo.co.tireerpserver.model.response.stock;

import com.minsoo.co.tireerpserver.entity.stock.Stock;
import com.minsoo.co.tireerpserver.model.response.management.WarehouseResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StockResponse {

    private Long stockId;
    private Long tireDotId;
    private WarehouseResponse warehouse;
    private String nickname;
    private Long quantity;
    private Boolean lock;

    public StockResponse(Stock stock) {
        this.stockId = stock.getId();
        this.tireDotId = stock.getTireDot().getId();
        this.warehouse = new WarehouseResponse(stock.getWarehouse());
        this.nickname = stock.getNickname();
        this.quantity = stock.getQuantity();
        this.lock = stock.getLock();
    }
}
