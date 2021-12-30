package com.minsoo.co.tireerpserver.model.response.stock;

import com.minsoo.co.tireerpserver.entity.stock.Stock;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class StockResponse {

    private Long stockId;
    private Long tireDotId;
    private Long warehouseId;
    private String nickname;
    private Integer quantity;
    private Boolean lock;

    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
    private String createdBy;
    private String lastModifiedBy;

    public StockResponse(Stock stock) {
        this.stockId = stock.getId();
        this.tireDotId = stock.getTireDot().getId();
        this.warehouseId = stock.getWarehouse().getId();
        this.nickname = stock.getNickname();
        this.quantity = stock.getQuantity();
        this.lock = stock.getLock();

        this.createdAt = stock.getCreatedAt();
        this.lastModifiedAt = stock.getLastModifiedAt();
        this.createdBy = stock.getCreatedBy();
        this.lastModifiedBy = stock.getLastModifiedBy();
    }
}
