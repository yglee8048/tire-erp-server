package com.minsoo.co.tireerpserver.model.response.sale;

import com.minsoo.co.tireerpserver.model.TireInfoResponse;
import com.minsoo.co.tireerpserver.model.response.client.ClientCompanyResponse;
import com.minsoo.co.tireerpserver.model.response.tire.TireDotGridResponse;
import com.minsoo.co.tireerpserver.model.response.stock.StockGridResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class SaleContentGridResponse {

    private Long saleContentId;
    private SaleResponse sale;

    private ClientCompanyResponse clientCompany;

    private TireInfoResponse tireInfo;

    private Long tireDotId;
    private TireDotGridResponse tireDotInfo;

    private Integer quantity;
    private Long price;
    private Long salePrice;

    private StockGridResponse stockGridResponse;

    private DeliveryResponse delivery;

    private String createdBy;
    private LocalDateTime createdAt;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedAt;

    public SaleContentGridResponse setTireDotInfo(TireDotGridResponse tireDotGridResponse) {
        this.tireDotInfo = tireDotGridResponse;
        return this;
    }
}
