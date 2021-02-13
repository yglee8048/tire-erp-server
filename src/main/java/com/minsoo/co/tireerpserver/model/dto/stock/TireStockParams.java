package com.minsoo.co.tireerpserver.model.dto.stock;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TireStockParams {

    @JsonProperty("sizes")
    private List<String> sizes;

    @JsonProperty("brand_names")
    private List<String> brandNames;

    @JsonProperty("patterns")
    private List<String> patterns;

    @JsonProperty("product_ids")
    private List<String> productIds;

    public TireStockParams(List<String> sizes, List<String> brandNames, List<String> patterns, List<String> productIds) {
        this.sizes = sizes;
        this.brandNames = brandNames;
        this.patterns = patterns;
        this.productIds = productIds;
    }
}
