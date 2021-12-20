package com.minsoo.co.tireerpserver.model.response.sale;

import com.minsoo.co.tireerpserver.constant.SaleSource;
import com.minsoo.co.tireerpserver.constant.SaleStatus;
import com.minsoo.co.tireerpserver.entity.sale.Sale;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class SaleResponse {

    private Long saleId;
    private Long clientCompanyId;
    private Long deliveryId;
    private SaleSource source;
    private SaleStatus status;
    private LocalDate transactionDate;
    private LocalDate releaseDate;
    private LocalDate desiredDeliveryDate;

    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
    private String createdBy;
    private String lastModifiedBy;

    public SaleResponse(Sale sale) {
        this.saleId = sale.getId();
        this.clientCompanyId = sale.getClientCompany().getId();
        this.deliveryId = sale.getDelivery().getId();
        this.source = sale.getSource();
        this.status = sale.getStatus();
        this.transactionDate = sale.getTransactionDate();
        this.releaseDate = sale.getReleaseDate();
        this.desiredDeliveryDate = sale.getDesiredDeliveryDate();

        this.createdAt = sale.getCreatedAt();
        this.lastModifiedAt = sale.getLastModifiedAt();
        this.createdBy = sale.getCreatedBy();
        this.lastModifiedBy = sale.getLastModifiedBy();
    }
}
