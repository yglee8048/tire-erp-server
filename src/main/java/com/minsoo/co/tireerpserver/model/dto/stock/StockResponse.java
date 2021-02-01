package com.minsoo.co.tireerpserver.model.dto.stock;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("open")
    private boolean open;
}
