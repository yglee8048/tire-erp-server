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

    @Schema(name = "타이어")
    private TireResponse tire;

    @Schema(name = "매입 평균가", example = "288500")
    private Double averageOfPurchasePrice;

    @Schema(name = "수량", example = "207")
    private Long sumOfStock;

    @Schema(name = "DOT 개수", example = "5")
    private Long numberOfDot;
}
