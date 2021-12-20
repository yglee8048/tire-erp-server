package com.minsoo.co.tireerpserver.model.response.tire;

import com.minsoo.co.tireerpserver.entity.tire.Tire;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TireResponse {

    private Long tireId;
    private String tireCode;
    private Boolean onSale;
    private Integer width;
    private Integer flatnessRatio;
    private Integer inch;
    private String size;
    private Integer loadIndex;
    private String speedIndex;
    private Long patternId;
    private Boolean runFlat;
    private Boolean sponge;
    private Boolean sealing;
    private String oe;
    private String originalVehicle;
    private String countryOfManufacture;
    private Integer retailPrice;

    private String tireGroup;
    private String note;
    private String pr;
    private String lr;
    private String tireRoId;

    private String createdBy;
    private LocalDateTime createdAt;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedAt;

    public TireResponse(Tire tire) {
        this.tireId = tire.getId();
        this.tireCode = tire.getTireCode();
        this.onSale = tire.getOnSale();
        this.width = tire.getWidth();
        this.flatnessRatio = tire.getFlatnessRatio();
        this.inch = tire.getInch();
        this.size = tire.getSize();
        this.loadIndex = tire.getLoadIndex();
        this.speedIndex = tire.getSpeedIndex();
        this.patternId = tire.getPattern().getId();
        this.runFlat = tire.getRunFlat();
        this.sponge = tire.getSponge();
        this.sealing = tire.getSealing();
        this.oe = tire.getOe();
        this.originalVehicle = tire.getOriginalVehicle();
        this.countryOfManufacture = tire.getCountryOfManufacture();
        this.retailPrice = tire.getRetailPrice();
        this.tireGroup = tire.getTireGroup();
        this.note = tire.getNote();
        this.pr = tire.getPr();
        this.lr = tire.getLr();
        this.tireRoId = tire.getTireRoId();

        this.createdAt = tire.getCreatedAt();
        this.lastModifiedAt = tire.getLastModifiedAt();
        this.createdBy = tire.getCreatedBy();
        this.lastModifiedBy = tire.getLastModifiedBy();
    }
}
