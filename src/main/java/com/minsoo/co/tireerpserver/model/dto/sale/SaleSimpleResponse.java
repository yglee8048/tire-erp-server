package com.minsoo.co.tireerpserver.model.dto.sale;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minsoo.co.tireerpserver.model.code.SaleSource;
import com.minsoo.co.tireerpserver.model.code.SaleStatus;
import com.minsoo.co.tireerpserver.model.dto.sale.content.SaleSimpleContentResponse;
import com.minsoo.co.tireerpserver.model.entity.Sale;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class SaleSimpleResponse {

    @ApiModelProperty(value = "매출 ID", example = "2991")
    @JsonProperty("sale_id")
    private Long saleId;

    @ApiModelProperty(value = "고객 ID", example = "2991")
    @JsonProperty("customer_id")
    private Long customerId;

    @ApiModelProperty(value = "생성 방식", example = "AUTO")
    @JsonProperty("source")
    private SaleSource source;

    @ApiModelProperty(value = "상태", example = "ACCEPTED")
    @JsonProperty("status")
    private SaleStatus status;

    @ApiModelProperty(value = "배달 ID", example = "2991")
    @JsonProperty("delivery_id")
    private Long deliveryId;

    @ApiModelProperty(value = "매출 항목")
    @JsonProperty("sale_contents")
    private List<SaleSimpleContentResponse> saleContents;

    public SaleSimpleResponse(Sale sale) {
        this.saleId = sale.getId();
        this.customerId = sale.getCustomer().getId();
        this.source = sale.getSource();
        this.status = sale.getStatus();
        this.deliveryId = sale.getDelivery() == null ? null : sale.getDelivery().getId();
        this.saleContents = sale.getSaleContents()
                .stream()
                .map(SaleSimpleContentResponse::of)
                .collect(Collectors.toList());
    }

    public static SaleSimpleResponse of(Sale sale) {
        return new SaleSimpleResponse(sale);
    }
}
