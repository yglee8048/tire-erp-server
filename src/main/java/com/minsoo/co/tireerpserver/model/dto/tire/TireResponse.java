package com.minsoo.co.tireerpserver.model.dto.tire;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minsoo.co.tireerpserver.model.code.TireOption;
import com.minsoo.co.tireerpserver.model.dto.brand.BrandResponse;
import com.minsoo.co.tireerpserver.model.entity.Tire;
import lombok.Data;

@Data
public class TireResponse {

    @JsonProperty("id")
    private Long id;

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

    public TireResponse(Tire tire) {
        this.id = tire.getId();
        this.brand = new BrandResponse(tire.getBrand());
        this.label = tire.getLabel();
        this.width = tire.getWidth();
        this.flatnessRatio = tire.getFlatnessRatio();
        this.inch = tire.getInch();
        this.loadIndex = tire.getLoadIndex();
        this.speedIndex = tire.getSpeedIndex();
        this.season = tire.getSeason();
        this.price = tire.getPrice();
        this.runFlat = tire.isRunFlat();
        this.option = tire.getOption();
        this.oe = tire.getOe();
    }
}
