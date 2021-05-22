package com.minsoo.co.tireerpserver.model.dto.stock;

import com.minsoo.co.tireerpserver.model.dto.tire.tire.TireSimpleResponse;
import lombok.*;

@Data
@NoArgsConstructor
public class TireStockResponse {

    // 타이어
    private TireSimpleResponse tire;

    // 매입 평균가
    private Double averageOfPurchasePrice;

    // 전체 재고 합
    private Long sumOfStock;

    // 공개 재고 합
    private Long sumOfOpenedStock;

    // 재고가 존재하는 DOT 수
    private Long numberOfActiveDot;
}
