package com.minsoo.co.tireerpserver.model.response.grid;

import com.minsoo.co.tireerpserver.constant.SaleStatus;
import com.minsoo.co.tireerpserver.entity.sale.SaleContent;
import com.minsoo.co.tireerpserver.model.TireStandardDTO;
import com.minsoo.co.tireerpserver.model.response.account.ClientCompanyResponse;
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
    private LocalDate confirmedDate;
    private LocalDate desiredDeliveryDate;
    private SaleStatus status;

    private ClientCompanyResponse clientCompany;

    private TireStandardDTO tireInfo;

    private Long sumOfOpenedStock;
    private Long sumOfStock;
    private Double averageOfPurchasePrice;
    private String dot;
    private Integer quantity;
    private Long price;
    private Long salePrice;

    private String createdBy;
    private LocalDateTime createdAt;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedAt;

    public SaleContentGridResponse(SaleContent saleContent) {
        this.saleContentId = saleContent.getId();
        this.saleId = saleContent.getSale().getId();
        this.transactionDate = saleContent.getSale().getTransactionDate();
        this.confirmedDate = saleContent.getSale().getReleaseDate();
        this.desiredDeliveryDate = saleContent.getSale().getDesiredDeliveryDate();
        this.status = saleContent.getSale().getStatus();
        this.clientCompany = new ClientCompanyResponse(saleContent.getSale().getClientCompany());
        this.tireInfo = new TireStandardDTO(saleContent.getTireDot().getTire());
        this.sumOfOpenedStock = saleContent.getTireDot().getSumOfOpenedStockQuantity();
        this.sumOfStock = saleContent.getTireDot().getSumOfStockQuantity();
        this.averageOfPurchasePrice = saleContent.getTireDot().getAveragePurchasePrice();
        this.dot = saleContent.getTireDot().getDot();
        this.quantity = saleContent.getQuantity();
        this.price = saleContent.getPrice();
        this.salePrice = this.quantity * this.price;

        this.createdAt = saleContent.getCreatedAt();
        this.lastModifiedAt = saleContent.getLastModifiedAt();
    }
}
