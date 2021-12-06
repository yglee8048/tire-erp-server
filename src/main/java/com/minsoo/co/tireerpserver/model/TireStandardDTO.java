package com.minsoo.co.tireerpserver.model;

import com.minsoo.co.tireerpserver.entity.tire.Tire;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TireStandardDTO {

    private Long tireId;
    private String tireCode;
    private Long brandId;
    private String brandName;
    private Boolean onSale;
    private Integer width;
    private Integer flatnessRatio;
    private Integer inch;
    private String size;
    private Integer loadIndex;
    private String speedIndex;
    private Long patternId;
    private String patternName;
    private Boolean runFlat;
    private Boolean sponge;
    private Boolean sealing;
    private String oe;
    private String originalVehicle;
    private String countryOfManufacture;
    private Integer retailPrice;

    private String tireGroup;
    private String note;
    private String season;
    private Boolean quietness;
    private Boolean rideQuality;
    private Boolean mileage;
    private Boolean handling;
    private Boolean breakingPower;
    private Boolean sports;
    private Boolean wetSurface;
    private String pr;
    private String lr;
    private String tireRoId;

    /**
     * tire & pattern & brand
     */
    public TireStandardDTO(Tire tire) {
        this.tireId = tire.getId();
        this.tireCode = tire.getTireCode();
        this.brandId = tire.getPattern().getBrand().getId();
        this.brandName = tire.getPattern().getBrand().getName();
        this.onSale = tire.getOnSale();
        this.width = tire.getWidth();
        this.flatnessRatio = tire.getFlatnessRatio();
        this.inch = tire.getInch();
        this.size = tire.getSize();
        this.loadIndex = tire.getLoadIndex();
        this.speedIndex = tire.getSpeedIndex();
        this.patternId = tire.getPattern().getId();
        this.patternName = tire.getPattern().getName();
        this.runFlat = tire.getRunFlat();
        this.sponge = tire.getSponge();
        this.sealing = tire.getSealing();
        this.oe = tire.getOe();
        this.originalVehicle = tire.getOriginalVehicle();
        this.countryOfManufacture = tire.getCountryOfManufacture();
        this.retailPrice = tire.getRetailPrice();

        this.tireGroup = tire.getTireGroup();
        this.note = tire.getNote();
        this.season = tire.getPattern().getSeason();
        this.quietness = tire.getPattern().getQuietness();
        this.rideQuality = tire.getPattern().getRideQuality();
        this.mileage = tire.getPattern().getMileage();
        this.handling = tire.getPattern().getHandling();
        this.breakingPower = tire.getPattern().getBreakingPower();
        this.sports = tire.getPattern().getSports();
        this.wetSurface = tire.getPattern().getWetSurface();
        this.pr = tire.getPr();
        this.lr = tire.getLr();
        this.tireRoId = tire.getTireRoId();
    }
}
