package com.minsoo.co.tireerpserver.model.dto.purchase.content;

import com.minsoo.co.tireerpserver.model.dto.stock.StockRequest;
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
public class PurchaseContentConfirmRequest {

    @Schema(name = "purchase_content_id")
    private Long purchaseContentId;

    @Schema(name = "stock_requests", description = "매입한 항목을 어떻게 이동할 것인지 결정")
    private List<StockRequest> stockRequests;
}