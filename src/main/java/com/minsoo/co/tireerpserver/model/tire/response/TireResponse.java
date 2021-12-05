package com.minsoo.co.tireerpserver.model.tire.response;

import com.minsoo.co.tireerpserver.entity.tire.Tire;
import com.minsoo.co.tireerpserver.model.management.response.PatternResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TireResponse {

    private Long id;
    private PatternResponse pattern;
    private String tireIdentification;
    private Boolean onSale;
    private Integer width;
    private Integer flatnessRatio;
    private Integer inch;
    private String size;
    private Integer loadIndex;
    private String speedIndex;
    private Integer retailPrice;
    private Boolean runFlat;
    private Boolean sponge;
    private Boolean sealing;
    private String oe;
    private String countryOfManufacture;
    private String originalVehicle;
    private String note;
    private String tireGroup;
    private String pr;
    private String lr;

    public TireResponse(Tire tire) {
        this.id = tire.getId();
        this.pattern = new PatternResponse(tire.getPattern());
        this.onSale = tire.getOnSale();
        this.tireIdentification = tire.getTireIdentification();
        this.width = tire.getWidth();
        this.flatnessRatio = tire.getFlatnessRatio();
        this.inch = tire.getInch();
        this.size = tire.getSize();
        this.loadIndex = tire.getLoadIndex();
        this.speedIndex = tire.getSpeedIndex();
        this.retailPrice = tire.getRetailPrice();
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
}
