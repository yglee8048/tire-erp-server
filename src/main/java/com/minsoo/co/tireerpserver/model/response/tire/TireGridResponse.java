package com.minsoo.co.tireerpserver.model.response.tire;

import com.minsoo.co.tireerpserver.model.TireInfoResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
                .mapToInt(TireDotGridResponse::getSumOfOpenedStock)
                .sum();
        this.sumOfStock = tireDotInfos.stream()
                .mapToInt(TireDotGridResponse::getSumOfStock)
                .sum();
        this.theNumberOfActiveDots = Long.valueOf(tireDotInfos.stream()
                .filter(tireDotGridResponse -> tireDotGridResponse.getSumOfStock() > 0)
                .count()).intValue();
        this.averageOfPurchasePrice = tireDotInfos.stream()
                .mapToDouble(TireDotGridResponse::getAverageOfPurchasePrice)
                .average().orElse(0);
    }
}
