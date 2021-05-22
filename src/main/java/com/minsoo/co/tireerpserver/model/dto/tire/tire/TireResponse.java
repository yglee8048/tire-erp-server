package com.minsoo.co.tireerpserver.model.dto.tire.tire;

import com.minsoo.co.tireerpserver.model.code.TireOption;
import com.minsoo.co.tireerpserver.model.dto.tire.pattern.PatternResponse;
import com.minsoo.co.tireerpserver.model.entity.entities.tire.Tire;
import com.minsoo.co.tireerpserver.model.entity.entities.tire.TireOptions;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class TireResponse {

    private Long tireId;

    private String productId;

    private PatternResponse pattern;

    private boolean onSale;

    private Integer width;

    private Integer flatnessRatio;

    private Integer inch;

    private String size;

    private Integer loadIndex;

    private String speedIndex;

    private List<TireOption> options;

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
        this.options = tire.getOptions().stream().map(TireOptions::getOption).collect(Collectors.toList());
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
