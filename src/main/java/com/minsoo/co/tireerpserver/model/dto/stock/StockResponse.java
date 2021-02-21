package com.minsoo.co.tireerpserver.model.dto.stock;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minsoo.co.tireerpserver.model.dto.management.warehouse.WarehouseSimpleResponse;
import com.minsoo.co.tireerpserver.model.dto.tire.dot.TireDotResponse;
import com.minsoo.co.tireerpserver.model.entity.Stock;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static lombok.AccessLevel.PROTECTED;

@Getter
@ToString
@NoArgsConstructor(access = PROTECTED)
public class StockResponse {

    @ApiModelProperty(value = "ID", example = "20039")
    @JsonProperty("stock_id")
    private Long StockId;

    @ApiModelProperty(value = "타이어 DOT")
    @JsonProperty("tire_dot")
    private TireDotResponse tireDot;

    @ApiModelProperty(value = "창고")
    @JsonProperty("warehouse")
    private WarehouseSimpleResponse warehouse;

    @ApiModelProperty(value = "수량", example = "250")
    @JsonProperty("quantity")
    private Long quantity;

    @ApiModelProperty(value = "잠금 여부(true=비공개/false=공개)", example = "true")
    @JsonProperty("lock")
    private boolean lock;

    private StockResponse(Stock stock) {
        StockId = stock.getId();
        this.tireDot = TireDotResponse.of(stock.getTireDot());
        this.warehouse = WarehouseSimpleResponse.of(stock.getWarehouse());
        this.quantity = stock.getQuantity();
        this.lock = stock.isLock();
    }

    public static StockResponse of(Stock stock) {
        return new StockResponse(stock);
    }
}
