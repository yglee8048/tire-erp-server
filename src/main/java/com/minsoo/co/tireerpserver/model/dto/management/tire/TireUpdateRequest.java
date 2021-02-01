package com.minsoo.co.tireerpserver.model.dto.management.tire;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minsoo.co.tireerpserver.model.code.TireOption;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class TireUpdateRequest {

    @NotEmpty
    @JsonProperty("tire_id")
    private Long tireId;

    @NotEmpty
    @JsonProperty("brand_id")
    private Long brandId;

    @JsonProperty("product_id")
    private String productId;

    @JsonProperty("label")
    private String label;

    @JsonProperty("width")
    private Integer width;

    @JsonProperty("flatness_ratio")
    private Integer flatnessRatio;

    @JsonProperty("inch")
    private Integer inch;

    @JsonProperty("pattern")
    private String pattern;

    @JsonProperty("load_index")
    private Integer loadIndex;

    @JsonProperty("speed_index")
    private String speedIndex;

    @JsonProperty("season")
    private String season;

    @JsonProperty("price")
    private Integer price;

    @JsonProperty("run_flat")
    private boolean runFlat;

    @JsonProperty("option")
    private TireOption option;

    @JsonProperty("oe")
    private String oe;
}
