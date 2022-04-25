package com.minsoo.co.tireerpserver.model.response.tire;

import com.minsoo.co.tireerpserver.entity.tire.Tire;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TireResponse {

    private Long tireId;
    private Long patternId;

    private String tireCode;
    private Integer width;
    private String flatnessRatio;
    private Integer inch;
    private String size;
    private String oe;
    private Integer loadIndex;
    private String speedIndex;
    private Boolean runFlat;
    private Boolean sponge;
    private Boolean sealing;
    private Long factoryPrice;
    private String countryOfManufacture;

    private String createdBy;
    private LocalDateTime createdAt;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedAt;

    public TireResponse(Tire tire) {
        this.tireId = tire.getId();
        this.patternId = tire.getPattern().getId();

        this.tireCode = tire.getTireCode();
        this.width = tire.getWidth();
        this.flatnessRatio = tire.getFlatnessRatio();
        this.inch = tire.getInch();
        this.size = tire.getSize();
        this.oe = tire.getOe();
        this.loadIndex = tire.getLoadIndex();
        this.speedIndex = tire.getSpeedIndex();
        this.runFlat = tire.getRunFlat();
        this.sponge = tire.getSponge();
        this.sealing = tire.getSealing();
        this.factoryPrice = tire.getFactoryPrice();
        this.countryOfManufacture = tire.getCountryOfManufacture();

        this.createdAt = tire.getCreatedAt();
        this.lastModifiedAt = tire.getLastModifiedAt();
        this.createdBy = tire.getCreatedBy();
        this.lastModifiedBy = tire.getLastModifiedBy();
    }
}
