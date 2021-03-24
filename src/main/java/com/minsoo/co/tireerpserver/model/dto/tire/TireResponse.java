package com.minsoo.co.tireerpserver.model.dto.tire;

import com.minsoo.co.tireerpserver.model.code.TireOption;
import com.minsoo.co.tireerpserver.model.dto.management.brand.BrandSimpleResponse;
import com.minsoo.co.tireerpserver.model.entity.Tire;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class TireResponse {

    @ApiModelProperty(value = "타이어 ID", example = "2991")
    private Long tireId;

    @ApiModelProperty(value = "브랜드")
    private BrandSimpleResponse brand;

    @ApiModelProperty(value = "상품 ID", example = "P2454518Z")
    private String productId;

    @ApiModelProperty(value = "레이블", example = "어슐런스 듀라플러스")
    private String label;

    @ApiModelProperty(value = "단면폭", example = "165", required = true)
    private Integer width;

    @ApiModelProperty(value = "편평비", example = "60", required = true)
    private Integer flatnessRatio;

    @ApiModelProperty(value = "인치", example = "14", required = true)
    private Integer inch;

    @ApiModelProperty(value = "사이즈", example = "'1656014'", required = true)
    private String size;

    @ApiModelProperty(value = "패턴", example = "Pzero")
    private String pattern;

    @ApiModelProperty(value = "하중지수", example = "79")
    private Integer loadIndex;

    @ApiModelProperty(value = "속도지수", example = "H")
    private String speedIndex;

    @ApiModelProperty(value = "계절", example = "3계절")
    private String season;

    @ApiModelProperty(value = "판매가", example = "300000")
    private Integer price;

    @ApiModelProperty(value = "런플렛", example = "true")
    private Boolean runFlat;

    @ApiModelProperty(value = "옵션", example = "SPONGE")
    private TireOption option;

    @ApiModelProperty(value = "OE", example = "AO")
    private String oe;

    private TireResponse(Tire tire) {
        this.tireId = tire.getId();
        this.brand = BrandSimpleResponse.of(tire.getBrand());
        this.productId = tire.getProductId();
        this.label = tire.getLabel();
        this.width = tire.getWidth();
        this.flatnessRatio = tire.getFlatnessRatio();
        this.inch = tire.getInch();
        this.size = tire.getSize();
        this.pattern = tire.getPattern();
        this.loadIndex = tire.getLoadIndex();
        this.speedIndex = tire.getSpeedIndex();
        this.season = tire.getSeason();
        this.price = tire.getPrice();
        this.runFlat = tire.getRunFlat();
        this.option = tire.getOption();
        this.oe = tire.getOe();
    }

    public static TireResponse of(Tire tire) {
        return new TireResponse(tire);
    }
}
