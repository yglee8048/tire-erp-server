package com.minsoo.co.tireerpserver.model.dto.sale.content;

import com.minsoo.co.tireerpserver.model.dto.tire.dot.TireDotResponse;
import com.minsoo.co.tireerpserver.model.entity.entities.sale.SaleContent;
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

    @Schema(name = "매출 항목 ID", example = "2991")
    private Long saleContentId;

    @Schema(name = "매출 ID", example = "2991")
    private Long saleId;

    @Schema(name = "타이어 DOT")
    private TireDotResponse tireDot;

    @Schema(name = "매출 수량", example = "20")
    private Long quantity;

    @Schema(name = "매출 금액", example = "240000")
    private Integer price;

    public SaleContentResponse(SaleContent saleContent) {
        this.saleContentId = saleContent.getId();
        this.saleId = saleContent.getSale().getId();
        this.tireDot = TireDotResponse.of(saleContent.getTireDot());
        this.quantity = saleContent.getQuantity();
        this.price = saleContent.getPrice();
    }

    public static SaleContentResponse of(SaleContent saleContent) {
        return new SaleContentResponse(saleContent);
    }
}
