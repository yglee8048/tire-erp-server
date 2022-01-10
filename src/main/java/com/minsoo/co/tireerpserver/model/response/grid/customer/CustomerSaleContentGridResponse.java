package com.minsoo.co.tireerpserver.model.response.grid.customer;

import com.minsoo.co.tireerpserver.constant.SaleStatus;
import com.minsoo.co.tireerpserver.model.TireStandardDTO;
import com.minsoo.co.tireerpserver.model.response.grid.SaleContentGridResponse;
import com.minsoo.co.tireerpserver.model.response.sale.DeliveryResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class CustomerSaleContentGridResponse {

    private Long saleContentId;
    private Long saleId;
    private LocalDate transactionDate;
    private LocalDate confirmedDate;
    private LocalDate desiredDeliveryDate;
    private SaleStatus status;

    private TireStandardDTO tireInfo;

    private Long tireDotId;
    private String dot;
    private Integer quantity;
    private Long price;
    private Long salePrice;

    private DeliveryResponse delivery;

    public CustomerSaleContentGridResponse(SaleContentGridResponse saleContentGridResponse) {
        this.saleContentId = saleContentGridResponse.getSaleContentId();
        this.saleId = saleContentGridResponse.getSaleId();
        this.transactionDate = saleContentGridResponse.getTransactionDate();
        this.confirmedDate = saleContentGridResponse.getReleaseDate();
        this.desiredDeliveryDate = saleContentGridResponse.getDesiredDeliveryDate();
        this.status = saleContentGridResponse.getStatus();
        this.tireInfo = saleContentGridResponse.getTireInfo();
        this.tireDotId = saleContentGridResponse.getTireDotId();
        this.dot = saleContentGridResponse.getDot();
        this.quantity = saleContentGridResponse.getQuantity();
        this.price = saleContentGridResponse.getPrice();
        this.salePrice = saleContentGridResponse.getSalePrice();
        this.delivery = saleContentGridResponse.getDelivery();
    }
}
