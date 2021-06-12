package com.minsoo.co.tireerpserver.sale.model.content;

import com.minsoo.co.tireerpserver.stock.model.StockRequest;
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
}
