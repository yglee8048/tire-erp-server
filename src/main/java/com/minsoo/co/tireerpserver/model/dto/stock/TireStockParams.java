package com.minsoo.co.tireerpserver.model.dto.stock;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Getter
@ToString
@NoArgsConstructor(access = PROTECTED)
public class TireStockParams {

    @ApiModelProperty(value = "사이즈")
    @JsonProperty("sizes")
    private List<String> sizes;

    @ApiModelProperty(value = "브랜드 이름")
    @JsonProperty("brand_names")
    private List<String> brandNames;

    @ApiModelProperty(value = "패턴")
    @JsonProperty("patterns")
    private List<String> patterns;

    @ApiModelProperty(value = "상품 ID")
    @JsonProperty("product_ids")
    private List<String> productIds;

    public TireStockParams(List<String> sizes, List<String> brandNames, List<String> patterns, List<String> productIds) {
        this.sizes = sizes;
        this.brandNames = brandNames;
        this.patterns = patterns;
        this.productIds = productIds;
    }
}
