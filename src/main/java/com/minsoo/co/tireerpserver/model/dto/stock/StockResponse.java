package com.minsoo.co.tireerpserver.model.dto.stock;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minsoo.co.tireerpserver.model.entity.Stock;
import lombok.Data;

@Data
public class StockResponse {

    @JsonProperty("stock_id")
    private Long StockId;

    @JsonProperty("tire_dot_id")
    private Long tireDotId;

    @JsonProperty("dot")
    private String dot;

    @JsonProperty("warehouse_id")
    private Long warehouseId;

    @JsonProperty("warehouse_name")
    private String warehouseName;

    @JsonProperty("quantity")
    private Long quantity;

    @JsonProperty("lock")
    private boolean lock;

    public StockResponse(Stock stock) {
        StockId = stock.getId();
        this.tireDotId = stock.getTireDot().getId();
        this.dot = stock.getTireDot().getDot();
        this.warehouseId = stock.getWarehouse().getId();
        this.warehouseName = stock.getWarehouse().getName();
        this.quantity = stock.getQuantity();
        this.lock = stock.isLock();
    }
}
