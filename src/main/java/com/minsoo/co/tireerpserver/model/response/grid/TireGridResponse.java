package com.minsoo.co.tireerpserver.model.response.grid;

import com.minsoo.co.tireerpserver.model.TireStandardDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TireGridResponse {

    private TireStandardDTO tireInfo;
    private Integer sumOfOpenedStock;
    private Integer sumOfStock;
    private Integer theNumberOfActiveDots;
    private Double averageOfPurchasePrice;
}
