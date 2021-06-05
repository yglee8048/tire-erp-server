package com.minsoo.co.tireerpserver.api.v1.model.dto.sale.content;

import com.minsoo.co.tireerpserver.services.sale.entity.SaleContent;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class SaleContentSimpleResponse {

    @Schema(description = "매출 항목 ID", example = "2991")
    private Long saleContentId;

    @Schema(description = "매출 ID", example = "2991")
    private Long saleId;

    @Schema(description = "타이어 DOT ID", example = "2991")
    private Long tireDotID;

    @Schema(description = "매출 수량", example = "20")
    private Long quantity;

    @Schema(description = "매출 금액", example = "240000")
    private Integer price;

    public SaleContentSimpleResponse(SaleContent saleContent) {
        this.saleContentId = saleContent.getId();
        this.saleId = saleContent.getSale().getId();
        this.tireDotID = saleContent.getTireDot().getId();
        this.quantity = saleContent.getQuantity();
        this.price = saleContent.getPrice();
    }

    public static SaleContentSimpleResponse of(SaleContent saleContent) {
        return new SaleContentSimpleResponse(saleContent);
    }
}