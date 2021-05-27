package com.minsoo.co.tireerpserver.model.dto.stock;

import com.minsoo.co.tireerpserver.model.dto.tire.TireSimpleResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
public class TireStockResponse {

    @Schema(name = "tire", description = "타이어 정보")
    private TireSimpleResponse tire;

    @Schema(name = "average_of_purchase_price", description = "매입 평균가")
    private Double averageOfPurchasePrice;

    @Schema(name = "sum_of_stock", description = "전체 재고")
    private Long sumOfStock;

    @Schema(name = "sum_of_opened_stock", description = "공개 재고")
    private Long sumOfOpenedStock;

    @Schema(name = "number_of_active_dot", description = "(재고가 존재하는) DOT 수")
    private Long numberOfActiveDot;
}
