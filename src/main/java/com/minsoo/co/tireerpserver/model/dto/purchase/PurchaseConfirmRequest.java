package com.minsoo.co.tireerpserver.model.dto.purchase;

import com.minsoo.co.tireerpserver.model.dto.stock.ModifyStockRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseConfirmRequest {

    @Schema(name = "purchase_content_id", description = "purchase_content_id")
    @NotNull(message = "purchase_content_id 는 필수 값입니다.")
    private Long purchaseContentId;

    @Schema(name = "stock_requests", description = "재고 이동 요청 목록")
    @NotNull(message = "stock_requests 는 필수 값입니다.")
    private List<ModifyStockRequest> stockRequests;
}
