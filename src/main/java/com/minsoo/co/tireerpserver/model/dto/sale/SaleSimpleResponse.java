package com.minsoo.co.tireerpserver.model.dto.sale;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minsoo.co.tireerpserver.model.code.SaleSource;
import com.minsoo.co.tireerpserver.model.code.SaleStatus;
import com.minsoo.co.tireerpserver.model.dto.customer.CustomerResponse;
import com.minsoo.co.tireerpserver.model.dto.sale.content.SaleContentResponse;
import com.minsoo.co.tireerpserver.model.entity.Delivery;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class SaleSimpleResponse {

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
}
