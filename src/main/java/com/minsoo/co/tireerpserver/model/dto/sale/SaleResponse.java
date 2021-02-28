package com.minsoo.co.tireerpserver.model.dto.sale;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minsoo.co.tireerpserver.model.code.SaleSource;
import com.minsoo.co.tireerpserver.model.code.SaleStatus;
import com.minsoo.co.tireerpserver.model.dto.customer.CustomerResponse;
import com.minsoo.co.tireerpserver.model.dto.sale.content.SaleContentResponse;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class SaleResponse {

    @JsonProperty("sale_id")
    private Long saleId;

    @JsonProperty("customer")
    private CustomerResponse customer;

    @JsonProperty("source")
    private SaleSource source;

    @JsonProperty("status")
    private SaleStatus status;

    @JsonProperty("delivery_id")
    private Long deliveryId;

    @JsonProperty("sale_id")
    private List<SaleContentResponse> saleContents;
}
