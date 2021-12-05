package com.minsoo.co.tireerpserver.model.stock.response;

import com.minsoo.co.tireerpserver.entity.stock.Stock;
import com.minsoo.co.tireerpserver.model.management.response.WarehouseResponse;
import com.minsoo.co.tireerpserver.model.tire.response.TireDotResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StockResponse {

    private Long stockId;
    private TireDotResponse tireDot;
    private WarehouseResponse warehouse;
    private String nickname;
    private Long quantity;
    private Boolean lock;

    public StockResponse(Stock stock) {
        this.stockId = stock.getId();
        this.tireDot = new TireDotResponse(stock.getTireDot());
        this.warehouse = new WarehouseResponse(stock.getWarehouse());
        this.nickname = stock.getNickname();
        this.quantity = stock.getQuantity();
        this.lock = stock.getLock();
    }
}
