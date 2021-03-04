package com.minsoo.co.tireerpserver.model.dto.sale.content;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minsoo.co.tireerpserver.model.dto.tire.dot.TireDotResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class SaleContentResponse {

    @ApiModelProperty(value = "매출 항목 ID", example = "2991")
    @JsonProperty("sale_content_id")
    private Long saleContentId;

    @ApiModelProperty(value = "매출 ID", example = "2991")
    @JsonProperty("sale_id")
    private Long saleId;

    @ApiModelProperty(value = "타이어 DOT")
    @JsonProperty("tire_dot")
    private TireDotResponse tireDot;

    @ApiModelProperty(value = "수량", example = "20")
    @JsonProperty("quantity")
    private Long quantity;

    @ApiModelProperty(value = "금액", example = "240000")
    @JsonProperty("price")
    private Long price;
}
