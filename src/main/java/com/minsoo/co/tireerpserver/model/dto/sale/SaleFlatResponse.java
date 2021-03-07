package com.minsoo.co.tireerpserver.model.dto.sale;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minsoo.co.tireerpserver.model.code.SaleSource;
import com.minsoo.co.tireerpserver.model.code.SaleStatus;
import com.minsoo.co.tireerpserver.model.dto.customer.CustomerResponse;
import com.minsoo.co.tireerpserver.model.dto.tire.dot.TireDotResponse;
import com.minsoo.co.tireerpserver.model.entity.Sale;
import com.minsoo.co.tireerpserver.model.entity.SaleContent;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class SaleFlatResponse {

    @ApiModelProperty(value = "매출 ID", example = "2991")
    @JsonProperty("sale_id")
    private Long saleId;

    @ApiModelProperty(value = "구입 고객")
    @JsonProperty("customer")
    private CustomerResponse customer;

    @ApiModelProperty(value = "매출 일자", example = "2021-02-18")
    @JsonProperty("sale_date")
    private LocalDate saleDate;

    @ApiModelProperty(value = "생성 방식", example = "AUTO")
    @JsonProperty("source")
    private SaleSource source;

    @ApiModelProperty(value = "매출 상태", example = "ACCEPTED")
    @JsonProperty("status")
    private SaleStatus status;

    @ApiModelProperty(value = "배달 ID", example = "2991")
    @JsonProperty("delivery_id")
    private Long deliveryId;

    @ApiModelProperty(value = "매출 항목 ID", example = "2991")
    @JsonProperty("sale_content_id")
    private Long saleContentId;

    @ApiModelProperty(value = "타이어 DOT")
    @JsonProperty("tire_dot")
    private TireDotResponse tireDot;

    @ApiModelProperty(value = "매출 수량", example = "20")
    @JsonProperty("quantity")
    private Long quantity;

    @ApiModelProperty(value = "매출 금액", example = "240000")
    @JsonProperty("price")
    private Integer price;

    public SaleFlatResponse(Sale sale, SaleContent saleContent) {
        this.saleId = sale.getId();
        this.customer = CustomerResponse.of(sale.getCustomer());
        this.saleDate = sale.getSaleDate();
        this.source = sale.getSource();
        this.status = sale.getStatus();
        this.deliveryId = sale.getDelivery() == null ? null : sale.getDelivery().getId();
        this.saleContentId = saleContent.getId();
        this.tireDot = TireDotResponse.of(saleContent.getTireDot());
        this.quantity = saleContent.getQuantity();
        this.price = saleContent.getPrice();
    }

    public static SaleFlatResponse of(Sale sale, SaleContent saleContent) {
        return new SaleFlatResponse(sale, saleContent);
    }
}
