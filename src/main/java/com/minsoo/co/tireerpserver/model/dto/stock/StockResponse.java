package com.minsoo.co.tireerpserver.model.dto.stock;

import com.minsoo.co.tireerpserver.model.dto.management.warehouse.WarehouseResponse;
import com.minsoo.co.tireerpserver.model.dto.tire.dot.TireDotResponse;
import com.minsoo.co.tireerpserver.model.entity.entities.stock.Stock;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class StockResponse {

    @Schema(name = "ID", example = "20039")
    private Long stockId;

    @Schema(name = "타이어 DOT")
    private TireDotResponse tireDot;

    @Schema(name = "창고")
    private WarehouseResponse warehouse;

    @Schema(name = "수량", example = "250")
    private Long quantity;

    @Schema(name = "잠금 여부(true=비공개/false=공개)", example = "true")
    private boolean lock;

    private StockResponse(Stock stock) {
        this.stockId = stock.getId();
        this.tireDot = TireDotResponse.of(stock.getTireDot());
        this.warehouse = WarehouseResponse.of(stock.getWarehouse());
        this.quantity = stock.getQuantity();
        this.lock = stock.isLock();
    }

    public static StockResponse of(Stock stock) {
        return new StockResponse(stock);
    }
}
