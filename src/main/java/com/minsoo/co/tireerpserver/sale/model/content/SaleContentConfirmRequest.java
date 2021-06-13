package com.minsoo.co.tireerpserver.sale.model.content;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleContentConfirmRequest {

    private Long saleContentId;

    private List<SaleContentStockRequest> stockRequests;
}
