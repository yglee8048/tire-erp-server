package com.minsoo.co.tireerpserver.model.request.stock;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleConfirmStockRequest {

    @Schema(name = "stock_id", description = "stock_id")
    private Long stockId;

    @Schema(name = "quantity", description = "수량")
    private Integer quantity;
}
