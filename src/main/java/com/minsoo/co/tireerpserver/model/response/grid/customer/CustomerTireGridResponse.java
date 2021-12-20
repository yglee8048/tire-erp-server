package com.minsoo.co.tireerpserver.model.response.grid.customer;

import com.minsoo.co.tireerpserver.model.TireStandardDTO;
import com.minsoo.co.tireerpserver.model.response.grid.TireGridResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerTireGridResponse {

    private TireStandardDTO tireInfo;
    private Long sumOfOpenedStock;
    private Long theNumberOfActiveDots;

    public CustomerTireGridResponse(TireGridResponse tireGridResponse) {
        this.tireInfo = tireGridResponse.getTireInfo();
        this.sumOfOpenedStock = tireGridResponse.getSumOfOpenedStock();
        this.theNumberOfActiveDots = tireGridResponse.getTheNumberOfActiveDots();
    }
}
