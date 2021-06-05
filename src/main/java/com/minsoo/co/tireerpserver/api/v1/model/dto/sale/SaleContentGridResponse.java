package com.minsoo.co.tireerpserver.api.v1.model.dto.sale;

import com.minsoo.co.tireerpserver.services.sale.code.SaleStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class SaleContentGridResponse {

    // ID
    @Schema(name = "sale_content_id", description = "sale_content_id")
    private Long saleContentId;

    @Schema(name = "sale_id", description = "sale_id")
    private Long saleId;

    @Schema(name = "customer_id", description = "customer_id")
    private Long customerId;

    @Schema(name = "delivery_id", description = "delivery_id")
    private Long deliveryId;

    @Schema(name = "tire_dot_id", description = "tire_dot_id")
    private Long tireDotId;

    @Schema(name = "tire_id", description = "tire_id")
    private Long tireId;

    @Schema(name = "pattern_id", description = "pattern_id")
    private Long patternId;

    @Schema(name = "brand_id", description = "brand_id")
    private Long brandId;

    // DISPLAY
    @Schema(name = "customer_name", description = "거래처 이름")
    private String customerName;

    @Schema(name = "transaction_date", description = "거래 일자")
    private LocalDate transactionDate;

    @Schema(name = "confirmed_date", description = "확정 일자")
    private LocalDate confirmedDate;

    @Schema(name = "desired_delivery_date", description = "배송 희망 일자")
    private LocalDate desiredDeliveryDate;

    @Schema(name = "sale_status", description = "매출 상태")
    private SaleStatus saleStatus;

    @Schema(name = "brand_name", description = "제조사 이름")
    private String brandName;

    @Schema(name = "pattern_name", description = "패턴 이름")
    private String patternName;

    @Schema(name = "tire_size", description = "타이어 사이즈(표기형식)")
    private String tireSize;

    @Schema(name = "dot", description = "DOT")
    private String dot;

    @Schema(name = "sale_content_quantity", description = "매출 항목 판매 개수")
    private Long saleContentQuantity;

    @Schema(name = "sale_content_price", description = "매출 항목 판매 가격")
    private Integer saleContentPrice;
}
