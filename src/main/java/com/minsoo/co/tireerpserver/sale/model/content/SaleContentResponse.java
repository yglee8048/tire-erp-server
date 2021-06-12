package com.minsoo.co.tireerpserver.sale.model.content;

import com.minsoo.co.tireerpserver.sale.entity.SaleContent;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
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
