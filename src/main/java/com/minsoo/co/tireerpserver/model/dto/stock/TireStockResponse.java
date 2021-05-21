package com.minsoo.co.tireerpserver.model.dto.stock;

import com.minsoo.co.tireerpserver.model.dto.tire.tire.TireResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class TireStockResponse {

    private TireResponse tire;

    // 매입 평균가
    private Double averageOfPurchasePrice;

    // 수량
    private Long sumOfStock;

    // DOT 수
    private Long numberOfDot;
}
