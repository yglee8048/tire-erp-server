package com.minsoo.co.tireerpserver.purchase.model.content;

import com.minsoo.co.tireerpserver.purchase.code.PurchaseStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class PurchaseContentGridResponse {

    // ID
    @Schema(name = "purchase_content_id", description = "purchase_content_id")
    private Long purchaseContentId;

    @Schema(name = "purchase_id", description = "purchase_id")
    private Long purchaseId;

    @Schema(name = "vendor_id", description = "vendor_id")
    private Long vendorId;

    @Schema(name = "tire_dot_id", description = "tire_dot_id")
    private Long tireDotId;

    @Schema(name = "tire_id", description = "tire_id")
    private Long tireId;

    @Schema(name = "pattern_id", description = "pattern_id")
    private Long patternId;

    @Schema(name = "brand_id", description = "brand_id")
    private Long brandId;

    // DISPLAY
    @Schema(name = "vendor_name", description = "매입처 이름")
    private String vendorName;

    @Schema(name = "purchase_status", description = "매입 상태")
    private PurchaseStatus purchaseStatus;

    @Schema(name = "transaction_date", description = "매입 일자")
    private LocalDate transactionDate;

    @Schema(name = "brand_name", description = "제조사 이름")
    private String brandName;

    @Schema(name = "pattern_name", description = "패턴 이름")
    private String patternName;

    @Schema(name = "tire_identification", description = "타이어 ID")
    private String tireIdentification;

    @Schema(name = "tire_size", description = "타이어 사이즈(표기형식)")
    private String tireSize;

    @Schema(name = "dot", description = "DOT")
    private String dot;

    @Schema(name = "purchase_content_price", description = "매입 항목 매입 가격")
    private Integer purchaseContentPrice;

    @Schema(name = "purchase_content_quantity", description = "매입 항목 매입 개수")
    private Long purchaseContentQuantity;
}
