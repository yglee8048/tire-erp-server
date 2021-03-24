package com.minsoo.co.tireerpserver.model.dto.sale;

import com.minsoo.co.tireerpserver.model.code.SaleSource;
import com.minsoo.co.tireerpserver.model.code.SaleStatus;
import com.minsoo.co.tireerpserver.model.dto.sale.content.SaleContentSimpleResponse;
import com.minsoo.co.tireerpserver.model.entity.Sale;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(value = "매출 ID", example = "2991")
    private Long saleId;

    @ApiModelProperty(value = "고객 ID", example = "2991")
    private Long customerId;

    @ApiModelProperty(value = "매출 일자", example = "2021-02-18")
    private LocalDate saleDate;

    @ApiModelProperty(value = "생성 방식", example = "AUTO")
    private SaleSource source;

    @ApiModelProperty(value = "매출 상태", example = "ACCEPTED")
    private SaleStatus status;

    @ApiModelProperty(value = "배달 ID", example = "2991")
    private Long deliveryId;

    @ApiModelProperty(value = "매출 항목")
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
