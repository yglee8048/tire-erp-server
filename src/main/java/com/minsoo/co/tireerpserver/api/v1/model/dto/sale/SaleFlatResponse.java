package com.minsoo.co.tireerpserver.api.v1.model.dto.sale;

import com.minsoo.co.tireerpserver.api.v1.model.dto.tire.dot.TireDotResponse;
import com.minsoo.co.tireerpserver.services.sale.code.SaleSource;
import com.minsoo.co.tireerpserver.services.sale.code.SaleStatus;
import com.minsoo.co.tireerpserver.api.v1.model.dto.account.customer.CustomerResponse;
import com.minsoo.co.tireerpserver.services.sale.entity.Sale;
import com.minsoo.co.tireerpserver.services.sale.entity.SaleContent;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "매출 ID", example = "2991")
    private Long saleId;

    @Schema(description = "구입 고객")
    private CustomerResponse customer;

    @Schema(description = "매출 일자", example = "2021-02-18")
    private LocalDate saleDate;

    @Schema(description = "생성 방식", example = "AUTO")
    private SaleSource source;

    @Schema(description = "매출 상태", example = "ACCEPTED")
    private SaleStatus status;

    @Schema(description = "배달 ID", example = "2991")
    private Long deliveryId;

    @Schema(description = "매출 항목 ID", example = "2991")
    private Long saleContentId;

    @Schema(description = "타이어 DOT")
    private TireDotResponse tireDot;

    @Schema(description = "매출 수량", example = "20")
    private Long quantity;

    @Schema(description = "매출 금액", example = "240000")
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

    public static SaleFlatResponse of(SaleContent saleContent) {
        return new SaleFlatResponse(saleContent.getSale(), saleContent);
    }
}