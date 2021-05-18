package com.minsoo.co.tireerpserver.model.dto.tire;

import com.minsoo.co.tireerpserver.model.code.TireOption;
import com.minsoo.co.tireerpserver.model.dto.management.brand.BrandSimpleResponse;
import com.minsoo.co.tireerpserver.model.entity.entities.tire.Tire;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class TireResponse {

    @Schema(name = "타이어 ID", example = "2991")
    private Long tireId;

    @Schema(name = "브랜드")
    private BrandSimpleResponse brand;

    @Schema(name = "상품 ID", example = "P2454518Z")
    private String productId;

    @Schema(name = "레이블", example = "어슐런스 듀라플러스")
    private String label;

    @Schema(name = "단면폭", example = "165", required = true)
    private Integer width;

    @Schema(name = "편평비", example = "60", required = true)
    private Integer flatnessRatio;

    @Schema(name = "인치", example = "14", required = true)
    private Integer inch;

    @Schema(name = "사이즈", example = "'1656014'", required = true)
    private String size;

    @Schema(name = "패턴", example = "Pzero")
    private String pattern;

    @Schema(name = "하중지수", example = "79")
    private Integer loadIndex;

    @Schema(name = "속도지수", example = "H")
    private String speedIndex;

    @Schema(name = "계절", example = "3계절")
    private String season;

    @Schema(name = "판매가", example = "300000")
    private Integer price;

    @Schema(name = "런플렛", example = "true")
    private Boolean runFlat;

    @Schema(name = "옵션", example = "SPONGE")
    private TireOption option;

    @Schema(name = "OE", example = "AO")
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
