package com.minsoo.co.tireerpserver.model.dto.tire;

import com.minsoo.co.tireerpserver.model.dto.management.pattern.PatternResponse;
import com.minsoo.co.tireerpserver.model.entity.tire.Tire;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
public class TireResponse {

    @Schema(name = "tire_id", description = "tire_id")
    private Long tireId;

    @Schema(name = "on_sale", description = "판매상태")
    private Boolean onSale;

    @Schema(name = "product_id", description = "타이어 ID")
    private String productId;

    @Schema(name = "pattern", description = "패턴 정보")
    private PatternResponse pattern;

    @Schema(name = "width", description = "단면폭")
    private Integer width;

    @Schema(name = "flatness_ratio", description = "편평비")
    private Integer flatnessRatio;

    @Schema(name = "inch", description = "인치")
    private Integer inch;

    @Schema(name = "size", description = "표기형식")
    private String size;

    @Schema(name = "load_index", description = "하중지수")
    private Integer loadIndex;

    @Schema(name = "speed_index", description = "속도지수")
    private String speedIndex;

    @Schema(name = "run_flat", description = "런플렛")
    private Boolean runFlat;

    @Schema(name = "sponge", description = "스펀지")
    private Boolean sponge;

    @Schema(name = "sealing", description = "실링")
    private Boolean sealing;

    @Schema(name = "oe", description = "OE 마크")
    private String oe;

    @Schema(name = "country_of_manufacture", description = "제조국")
    private String countryOfManufacture;

    @Schema(name = "original_vehicle", description = "순정 차량")
    private String originalVehicle;

    @Schema(name = "note", description = "비고")
    private String note;

    @Schema(name = "tire_group", description = "그룹")
    private String tireGroup;

    @Schema(name = "pr", description = "pr")
    private String pr;

    @Schema(name = "lr", description = "lr")
    private String lr;

    public TireResponse(Tire tire) {
        this.tireId = tire.getId();
        this.productId = tire.getProductId();
        this.pattern = PatternResponse.of(tire.getPattern());
        this.onSale = tire.getOnSale();
        this.width = tire.getWidth();
        this.flatnessRatio = tire.getFlatnessRatio();
        this.inch = tire.getInch();
        this.size = tire.getSize();
        this.loadIndex = tire.getLoadIndex();
        this.speedIndex = tire.getSpeedIndex();
        this.runFlat = tire.getRunFlat();
        this.sponge = tire.getSponge();
        this.sealing = tire.getSealing();
        this.oe = tire.getOe();
        this.countryOfManufacture = tire.getCountryOfManufacture();
        this.originalVehicle = tire.getOriginalVehicle();
        this.note = tire.getNote();
        this.tireGroup = tire.getTireGroup();
        this.pr = tire.getPr();
        this.lr = tire.getLr();
    }

    public static TireResponse of(Tire tire) {
        return new TireResponse(tire);
    }
}
