package com.minsoo.co.tireerpserver.model.response.grid;

import com.minsoo.co.tireerpserver.constant.SaleStatus;
import com.minsoo.co.tireerpserver.model.TireStandardDTO;
import com.minsoo.co.tireerpserver.model.response.client.ClientCompanyResponse;
import com.minsoo.co.tireerpserver.model.response.sale.DeliveryResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class SaleContentGridResponse {

    private Long saleContentId;
    private Long saleId;
    private LocalDate transactionDate;
    private LocalDate releaseDate;
    private LocalDate desiredDeliveryDate;
    private SaleStatus status;

    private ClientCompanyResponse clientCompany;

    private TireStandardDTO tireInfo;

    private Long tireDotId;
    private String dot;
    private Integer sumOfOpenedStock;
    private Integer sumOfStock;
    private Double averageOfPurchasePrice;
    private Integer quantity;
    private Long price;
    private Long salePrice;

    private Long stockId;
    private String stockNickname;
    private Long warehouseId;
    private String warehouseName;

    private DeliveryResponse delivery;

    private String createdBy;
    private LocalDateTime createdAt;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedAt;

    public SaleContentGridResponse setTireDotGrid(TireDotGridResponse tireDotGridResponse) {
        this.dot = tireDotGridResponse.getDot();
        this.sumOfOpenedStock = tireDotGridResponse.getSumOfOpenedStock();
        this.sumOfStock = tireDotGridResponse.getSumOfStock();
        this.averageOfPurchasePrice = tireDotGridResponse.getAverageOfPurchasePrice();
        return this;
    }
}
