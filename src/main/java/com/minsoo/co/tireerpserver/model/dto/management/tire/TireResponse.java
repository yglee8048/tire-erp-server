package com.minsoo.co.tireerpserver.model.dto.management.tire;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minsoo.co.tireerpserver.model.code.TireOption;
import com.minsoo.co.tireerpserver.model.entity.Tire;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static lombok.AccessLevel.PROTECTED;

@Getter
@ToString
@NoArgsConstructor(access = PROTECTED)
public class TireResponse {

    @JsonProperty("tire_id")
    private Long tireId;

    @JsonProperty("brand_id")
    private Long brandId;

    @JsonProperty("brand_name")
    private String brandName;

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

    private TireResponse(Tire tire) {
        this.tireId = tire.getId();
        this.brandId = tire.getBrand().getId();
        this.brandName = tire.getBrand().getName();
        this.productId = tire.getProductId();
        this.label = tire.getLabel();
        this.width = tire.getWidth();
        this.flatnessRatio = tire.getFlatnessRatio();
        this.pattern = tire.getPattern();
        this.inch = tire.getInch();
        this.loadIndex = tire.getLoadIndex();
        this.speedIndex = tire.getSpeedIndex();
        this.season = tire.getSeason();
        this.price = tire.getPrice();
        this.runFlat = tire.isRunFlat();
        this.option = tire.getOption();
        this.oe = tire.getOe();
    }

    public static TireResponse of(Tire tire) {
        return new TireResponse(tire);
    }
}
