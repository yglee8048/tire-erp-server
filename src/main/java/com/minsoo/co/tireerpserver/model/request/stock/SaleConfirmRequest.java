package com.minsoo.co.tireerpserver.model.request.stock;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleConfirmRequest {

    @Schema(name = "sale_content_id", description = "sale_content_id")
    private Long saleContentId;

    @Schema(name = "stocks", description = "재고 반영 내용")
    private List<SaleConfirmStockRequest> stocks;
}
