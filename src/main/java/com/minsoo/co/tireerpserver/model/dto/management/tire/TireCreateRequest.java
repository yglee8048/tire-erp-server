package com.minsoo.co.tireerpserver.model.dto.management.tire;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minsoo.co.tireerpserver.model.code.TireOption;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class TireCreateRequest {

    @NotNull
    @JsonProperty("brand_id")
    private Long brandId;

    @NotNull
    @JsonProperty("product_id")
    private String productId;

    @JsonProperty("label")
    private String label;

    @NotNull
    @Positive
    @JsonProperty("width")
    private Integer width;

    @NotNull
    @Positive
    @JsonProperty("flatness_ratio")
    private Integer flatnessRatio;

    @NotNull
    @Positive
    @JsonProperty("inch")
    private Integer inch;

    @NotNull
    @JsonProperty("pattern")
    private String pattern;

    @Positive
    @JsonProperty("load_index")
    private Integer loadIndex;

    @JsonProperty("speed_index")
    private String speedIndex;

    @JsonProperty("season")
    private String season;

    @Positive
    @JsonProperty("price")
    private Integer price;

    @JsonProperty("run_flat")
    private boolean runFlat;

    @JsonProperty("option")
    private TireOption option;

    @JsonProperty("oe")
    private String oe;
}
