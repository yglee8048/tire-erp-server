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
public class SaleContentResponse {

    @Schema(name = "sale_content_id", description = "sale_content_id")
    private Long saleContentId;

    @Schema(name = "sale_id", description = "매출")
    private Long saleId;

    @Schema(name = "tire_dot_id", description = "타이어 DOT")
    private Long tireDotId;

    @Schema(name = "quantity", description = "매출 수량", example = "20")
    private Long quantity;

    @Schema(name = "price", description = "매출 금액", example = "240000")
    private Integer price;

    public SaleContentResponse(SaleContent saleContent) {
        this.saleContentId = saleContent.getId();
        this.saleId = saleContent.getSale().getId();
        this.tireDotId = saleContent.getTireDot().getId();
        this.quantity = saleContent.getQuantity();
        this.price = saleContent.getPrice();
    }

    public static SaleContentResponse of(SaleContent saleContent) {
        return new SaleContentResponse(saleContent);
    }
}
