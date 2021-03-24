package com.minsoo.co.tireerpserver.model.dto.stock;

import com.minsoo.co.tireerpserver.model.dto.management.warehouse.WarehouseSimpleResponse;
import com.minsoo.co.tireerpserver.model.dto.tire.dot.TireDotSimpleResponse;
import com.minsoo.co.tireerpserver.model.entity.Stock;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class StockSimpleResponse {

    @ApiModelProperty(value = "ID", example = "20039")
    private Long stockId;

    @ApiModelProperty(value = "타이어 DOT")
    private TireDotSimpleResponse tireDot;

    @ApiModelProperty(value = "창고")
    private WarehouseSimpleResponse warehouse;

    @ApiModelProperty(value = "수량", example = "250")
    private Long quantity;

    @ApiModelProperty(value = "잠금 여부(true=비공개/false=공개)", example = "true")
    private boolean lock;

    private StockSimpleResponse(Stock stock) {
        this.stockId = stock.getId();
        this.tireDot = TireDotSimpleResponse.of(stock.getTireDot());
        this.warehouse = WarehouseSimpleResponse.of(stock.getWarehouse());
        this.quantity = stock.getQuantity();
        this.lock = stock.isLock();
    }

    public static StockSimpleResponse of(Stock stock) {
        return new StockSimpleResponse(stock);
    }
}
