package com.minsoo.co.tireerpserver.model.dto.stock;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class MoveStockRequest {

    @NotEmpty
    @JsonProperty("from_stock_id")
    private Long fromStockId;

    @NotEmpty
    @JsonProperty("to_warehouse_name")
    private String toWarehouseName;

    @NotNull
    @Positive
    @JsonProperty("quantity")
    private Long quantity;
}
