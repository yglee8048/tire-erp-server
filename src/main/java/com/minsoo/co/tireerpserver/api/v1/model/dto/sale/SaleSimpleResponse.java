package com.minsoo.co.tireerpserver.api.v1.model.dto.sale;

import com.minsoo.co.tireerpserver.api.v1.model.dto.sale.content.SaleContentSimpleResponse;
import com.minsoo.co.tireerpserver.services.sale.code.SaleSource;
import com.minsoo.co.tireerpserver.services.sale.code.SaleStatus;
import com.minsoo.co.tireerpserver.services.sale.entity.Sale;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class SaleSimpleResponse {

    @Schema(description = "매출 ID", example = "2991")
    private Long saleId;

    @Schema(description = "고객 ID", example = "2991")
    private Long customerId;

    @Schema(description = "매출 일자", example = "2021-02-18")
    private LocalDate saleDate;

    @Schema(description = "생성 방식", example = "AUTO")
    private SaleSource source;

    @Schema(description = "매출 상태", example = "ACCEPTED")
    private SaleStatus status;

    @Schema(description = "배달 ID", example = "2991")
    private Long deliveryId;

    @Schema(description = "매출 항목")
    private List<SaleContentSimpleResponse> saleContents;

    public SaleSimpleResponse(Sale sale) {
        this.saleId = sale.getId();
        this.customerId = sale.getCustomer().getId();
        this.saleDate = sale.getSaleDate();
        this.source = sale.getSource();
        this.status = sale.getStatus();
        this.deliveryId = sale.getDelivery() == null ? null : sale.getDelivery().getId();
        this.saleContents = sale.getSaleContents()
                .stream()
                .map(SaleContentSimpleResponse::of)
                .collect(Collectors.toList());
    }

    public static SaleSimpleResponse of(Sale sale) {
        return new SaleSimpleResponse(sale);
    }
}