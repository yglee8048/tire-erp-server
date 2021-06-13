package com.minsoo.co.tireerpserver.sale.model.content;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleContentStockRequest {

    private Long stockId;

    private Long quantity;
}
