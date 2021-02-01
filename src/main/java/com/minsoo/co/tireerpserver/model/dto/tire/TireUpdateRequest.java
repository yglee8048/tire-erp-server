package com.minsoo.co.tireerpserver.model.dto.tire;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minsoo.co.tireerpserver.model.code.TireOption;
import com.minsoo.co.tireerpserver.model.dto.brand.BrandResponse;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class TireUpdateRequest {

    @NotEmpty
    @JsonProperty("id")
    private Long id;

    @NotEmpty
    @JsonProperty("brand")
    private BrandResponse brand;

    @JsonProperty("label")
    private String label;

    @JsonProperty("width")
    private Integer width;

    @JsonProperty("flatness_ratio")
    private Integer flatnessRatio;

    @JsonProperty("inch")
    private Integer inch;

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
