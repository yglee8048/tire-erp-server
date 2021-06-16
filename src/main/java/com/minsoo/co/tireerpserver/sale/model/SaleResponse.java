package com.minsoo.co.tireerpserver.sale.model;

import com.minsoo.co.tireerpserver.sale.code.SaleSource;
import com.minsoo.co.tireerpserver.sale.code.SaleStatus;
import com.minsoo.co.tireerpserver.sale.entity.Sale;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleResponse {

    @Schema(name = "sale_id", description = "sale_id", example = "2991")
    private Long saleId;

    @Schema(name = "client_company_id", description = "client_company_id")
    private Long clientCompanyId;

    @Schema(name = "transaction_date", description = "거래 일자", example = "2021-02-18")
    private LocalDate transactionDate;

    @Schema(name = "confirmed_date", description = "확정 일자")
    private LocalDate confirmedDate;

    @Schema(name = "desired_delivery_date", description = "배송 희망 일자")
    private LocalDate desiredDeliveryDate;

    @Schema(name = "source", description = "생성 방식", example = "AUTO")
    private SaleSource source;

    @Schema(name = "status", description = "매출 상태", example = "ACCEPTED")
    private SaleStatus status;

    @Schema(name = "delivery_id", description = "delivery_id", example = "2991")
    private Long deliveryId;

    public static SaleResponse of(Sale sale) {
        return SaleResponse.builder()
                .saleId(sale.getId())
                .clientCompanyId(sale.getClientCompany().getId())
                .transactionDate(sale.getTransactionDate())
                .confirmedDate(sale.getConfirmedDate())
                .desiredDeliveryDate(sale.getDesiredDeliveryDate())
                .source(sale.getSource())
                .status(sale.getStatus())
                .deliveryId(sale.getDelivery().getId())
                .build();
    }
}
