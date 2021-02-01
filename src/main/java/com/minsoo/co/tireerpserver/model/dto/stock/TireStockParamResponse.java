package com.minsoo.co.tireerpserver.model.dto.stock;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TireStockParamResponse {

    @JsonProperty("sizes")
    private List<String> sizes = new ArrayList<>();

    @JsonProperty("brand_names")
    private List<String> brandNames = new ArrayList<>();

    @JsonProperty("patterns")
    private List<String> patterns = new ArrayList<>();

    @JsonProperty("product_ids")
    private List<String> productIds = new ArrayList<>();

    public void addParams(TireStockParams stockParams) {
        this.sizes.add(stockParams.getSize());
        this.brandNames.add(stockParams.getBrandName());
        this.patterns.add(stockParams.getPattern());
        this.productIds.add(stockParams.getProductId());
    }
}
