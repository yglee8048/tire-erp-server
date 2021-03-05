package com.minsoo.co.tireerpserver.model.dto.sale;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minsoo.co.tireerpserver.model.code.SaleSource;
import com.minsoo.co.tireerpserver.model.code.SaleStatus;
import com.minsoo.co.tireerpserver.model.dto.customer.CustomerResponse;
import com.minsoo.co.tireerpserver.model.dto.sale.content.SaleContentResponse;
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
public class SaleResponse {

    @ApiModelProperty(value = "매출 ID", example = "2991")
    @JsonProperty("sale_id")
    private Long saleId;

    @ApiModelProperty(value = "고객")
    @JsonProperty("customer")
    private CustomerResponse customer;

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
    private List<SaleContentResponse> saleContents;

    public SaleResponse(Sale sale) {
        this.saleId = sale.getId();
        this.customer = CustomerResponse.of(sale.getCustomer());
        this.source = sale.getSource();
        this.status = sale.getStatus();
        this.deliveryId = sale.getDelivery().getId();
        this.saleContents = sale.getSaleContents()
                .stream()
                .map(SaleContentResponse::of)
                .collect(Collectors.toList());
    }

    public static SaleResponse of(Sale sale) {
        return new SaleResponse(sale);
    }
}
