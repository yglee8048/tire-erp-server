package com.minsoo.co.tireerpserver.model.response.grid;

import com.minsoo.co.tireerpserver.constant.PurchaseStatus;
import com.minsoo.co.tireerpserver.model.TireStandardDTO;
import com.minsoo.co.tireerpserver.model.response.management.VendorResponse;
import com.minsoo.co.tireerpserver.model.response.management.WarehouseResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PurchaseContentGridResponse {

    private Long purchaseContentId;
    private Long purchaseId;
    private LocalDate transactionDate;
    private PurchaseStatus status;

    private VendorResponse vendor;

    private TireStandardDTO tireInfo;

    private Long tireDotId;
    private String dot;
    private Long sumOfOpenedStock;
    private Long sumOfStock;
    private Double averageOfPurchasePrice;
    private WarehouseResponse warehouse;
    private Integer quantity;
    private Long price;
    private Long purchasePrice;

    private String createdBy;
    private LocalDateTime createdAt;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedAt;

    public PurchaseContentGridResponse setTireDotGrid(TireDotGridResponse tireDotGridResponse) {
        this.dot = tireDotGridResponse.getDot();
        this.sumOfOpenedStock = tireDotGridResponse.getSumOfOpenedStock();
        this.sumOfStock = tireDotGridResponse.getSumOfStock();
        this.averageOfPurchasePrice = tireDotGridResponse.getAverageOfPurchasePrice();
        return this;
    }
}
