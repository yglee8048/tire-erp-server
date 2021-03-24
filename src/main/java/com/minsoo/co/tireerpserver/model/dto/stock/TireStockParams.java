package com.minsoo.co.tireerpserver.model.dto.stock;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class TireStockParams {

    @ApiModelProperty(value = "사이즈")
    private List<String> sizes;

    @ApiModelProperty(value = "브랜드 이름")
    private List<String> brandNames;

    @ApiModelProperty(value = "패턴")
    private List<String> patterns;

    @ApiModelProperty(value = "상품 ID")
    private List<String> productIds;

    public TireStockParams(List<String> sizes, List<String> brandNames, List<String> patterns, List<String> productIds) {
        this.sizes = sizes;
        this.brandNames = brandNames;
        this.patterns = patterns;
        this.productIds = productIds;
    }
}
