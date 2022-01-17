package com.minsoo.co.tireerpserver.model.response.sale;

import com.minsoo.co.tireerpserver.model.TireInfoResponse;
import com.minsoo.co.tireerpserver.model.response.client.ClientCompanyResponse;
import com.minsoo.co.tireerpserver.model.response.tire.CustomerTireDotGridResponse;
import com.minsoo.co.tireerpserver.model.response.stock.StockGridResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CustomerSaleContentGridResponse {

    private Long saleContentId;
    private SaleResponse sale;

    private ClientCompanyResponse clientCompany;

    private TireInfoResponse tireInfo;

    private Long tireDotId;
    private CustomerTireDotGridResponse tireDotInfo;

    private Integer quantity;
    private Long price;
    private Long salePrice;

    private StockGridResponse stockInfo;

    private DeliveryResponse delivery;

    private String createdBy;
    private LocalDateTime createdAt;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedAt;

    public CustomerSaleContentGridResponse(SaleContentGridResponse saleContentGridResponse) {
        this.saleContentId = saleContentGridResponse.getSaleContentId();
        this.sale = saleContentGridResponse.getSale();
        this.clientCompany = saleContentGridResponse.getClientCompany();
        this.tireInfo = saleContentGridResponse.getTireInfo();
        this.tireDotId = saleContentGridResponse.getTireDotId();
        this.tireDotInfo = new CustomerTireDotGridResponse(saleContentGridResponse.getTireDotInfo());
        this.quantity = saleContentGridResponse.getQuantity();
        this.price = saleContentGridResponse.getPrice();
        this.salePrice = saleContentGridResponse.getSalePrice();
        this.stockInfo = saleContentGridResponse.getStockInfo();
        this.delivery = saleContentGridResponse.getDelivery();
        this.createdBy = saleContentGridResponse.getCreatedBy();
        this.createdAt = saleContentGridResponse.getCreatedAt();
        this.lastModifiedBy = saleContentGridResponse.getLastModifiedBy();
        this.lastModifiedAt = saleContentGridResponse.getLastModifiedAt();
    }
}
