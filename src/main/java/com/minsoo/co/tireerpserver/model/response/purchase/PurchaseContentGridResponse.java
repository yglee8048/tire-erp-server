package com.minsoo.co.tireerpserver.model.response.purchase;

import com.minsoo.co.tireerpserver.model.TireInfoResponse;
import com.minsoo.co.tireerpserver.model.response.management.VendorResponse;
import com.minsoo.co.tireerpserver.model.response.stock.StockGridResponse;
import com.minsoo.co.tireerpserver.model.response.tire.TireDotGridResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PurchaseContentGridResponse {

    private Long purchaseContentId;
    private PurchaseResponse purchase;

    private VendorResponse vendor;

    private TireInfoResponse tireInfo;

    private Long tireDotId;
    private TireDotGridResponse tireDot;

    private Integer quantity;
    private Long price;
    private Long purchasePrice;

    private StockGridResponse stockGrid;

    private String createdBy;
    private LocalDateTime createdAt;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedAt;

    public PurchaseContentGridResponse setTireDotGrid(TireDotGridResponse tireDotGridResponse) {
        this.tireDot = tireDotGridResponse;
        return this;
    }
}
