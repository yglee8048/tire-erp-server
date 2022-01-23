package com.minsoo.co.tireerpserver.model.response.tire;

import com.minsoo.co.tireerpserver.model.TireInfoResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
public class TireGridResponse {

    private TireInfoResponse tireInfo;

    private Integer sumOfOpenedStock;
    private Integer sumOfStock;
    private Integer theNumberOfActiveDots;

    private Double averageOfPurchasePrice;

    public TireGridResponse(TireInfoResponse tireInfo, List<TireDotGridResponse> tireDotInfos) {
        this.tireInfo = tireInfo;
        this.sumOfOpenedStock = tireDotInfos.stream()
                .map(TireDotGridResponse::getSumOfOpenedStock)
                .filter(Objects::nonNull)
                .mapToInt(value -> value)
                .sum();
        this.sumOfStock = tireDotInfos.stream()
                .map(TireDotGridResponse::getSumOfStock)
                .filter(Objects::nonNull)
                .mapToInt(value -> value)
                .sum();
        this.theNumberOfActiveDots = Long.valueOf(tireDotInfos.stream()
                .map(TireDotGridResponse::getSumOfStock)
                .filter(Objects::nonNull)
                .filter(value -> value > 0)
                .count()).intValue();
        this.averageOfPurchasePrice = tireDotInfos.stream()
                .map(TireDotGridResponse::getAverageOfPurchasePrice)
                .filter(Objects::nonNull)
                .mapToDouble(value -> value)
                .average().orElse(0);
    }
}
