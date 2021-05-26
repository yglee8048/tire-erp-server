package com.minsoo.co.tireerpserver.model.dto.tire.tire;

import com.minsoo.co.tireerpserver.model.dto.management.pattern.PatternResponse;
import com.minsoo.co.tireerpserver.model.entity.entities.tire.Tire;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
public class TireResponse {

    private Long tireId;

    private Boolean onSale;

    private String productId;

    private PatternResponse pattern;

    private Integer width;

    private Integer flatnessRatio;

    private Integer inch;

    private String size;

    private Integer loadIndex;

    private String speedIndex;

    @Schema(description = "런플렛")
    private Boolean runFlat;

    @Schema(description = "스펀지")
    private Boolean sponge;

    @Schema(description = "실링")
    private Boolean sealing;

    private String oe;

    private String countryOfManufacture;

    private String originalVehicle;

    private String note;

    private String group;

    private String pr;

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
        this.group = tire.getGroup();
        this.pr = tire.getPr();
        this.lr = tire.getLr();
    }

    public static TireResponse of(Tire tire) {
        return new TireResponse(tire);
    }
}
