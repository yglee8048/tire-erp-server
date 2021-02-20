package com.minsoo.co.tireerpserver.model.dto.tire;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minsoo.co.tireerpserver.model.code.TireOption;
import com.minsoo.co.tireerpserver.model.dto.management.brand.BrandSimpleResponse;
import com.minsoo.co.tireerpserver.model.entity.Tire;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static lombok.AccessLevel.PROTECTED;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = PROTECTED)
public class TireResponse {

    @ApiModelProperty(value = "ID", example = "2991")
    @JsonProperty("tire_id")
    private Long tireId;

    @ApiModelProperty(value = "브랜드")
    @JsonProperty("brand")
    private BrandSimpleResponse brand;

    @ApiModelProperty(value = "상품 ID", example = "P2454518Z")
    @JsonProperty("product_id")
    private String productId;

    @ApiModelProperty(value = "레이블", example = "어슐런스 듀라플러스")
    @JsonProperty("label")
    private String label;

    @ApiModelProperty(value = "단면폭", example = "165")
    @JsonProperty("width")
    private Integer width;

    @ApiModelProperty(value = "편평비", example = "60")
    @JsonProperty("flatness_ratio")
    private Integer flatnessRatio;

    @ApiModelProperty(value = "인치", example = "14")
    @JsonProperty("inch")
    private Integer inch;

    @ApiModelProperty(value = "패턴", example = "Pzero")
    @JsonProperty("pattern")
    private String pattern;

    @ApiModelProperty(value = "하중지수", example = "79")
    @JsonProperty("load_index")
    private Integer loadIndex;

    @ApiModelProperty(value = "속도지수", example = "H")
    @JsonProperty("speed_index")
    private String speedIndex;

    @ApiModelProperty(value = "계절", example = "3계절")
    @JsonProperty("season")
    private String season;

    @ApiModelProperty(value = "판매가", example = "300000")
    @JsonProperty("price")
    private Integer price;

    @ApiModelProperty(value = "런플렛", example = "true")
    @JsonProperty("run_flat")
    private boolean runFlat;

    @ApiModelProperty(value = "옵션", example = "SPONGE")
    @JsonProperty("option")
    private TireOption option;

    @ApiModelProperty(value = "OE", example = "AO")
    @JsonProperty("oe")
    private String oe;

    private TireResponse(Tire tire) {
        this.tireId = tire.getId();
        this.brand = BrandSimpleResponse.of(tire.getBrand());
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
