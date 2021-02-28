package com.minsoo.co.tireerpserver.model.dto.sale.content;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minsoo.co.tireerpserver.model.dto.tire.dot.TireDotResponse;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class SaleContentResponse {

    @JsonProperty("sale_content_id")
    private Long saleContentId;

    @JsonProperty("sale_id")
    private Long saleId;

    @JsonProperty("tire_dot")
    private TireDotResponse tireDot;

    @JsonProperty("quantity")
    private Long quantity;

    @JsonProperty("price")
    private Long price;
}
