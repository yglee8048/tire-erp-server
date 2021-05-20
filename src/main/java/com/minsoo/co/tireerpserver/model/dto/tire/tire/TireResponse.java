package com.minsoo.co.tireerpserver.model.dto.tire.tire;

import com.minsoo.co.tireerpserver.model.code.TireOption;
import com.minsoo.co.tireerpserver.model.dto.management.brand.BrandSimpleResponse;
import com.minsoo.co.tireerpserver.model.dto.tire.pattern.PatternResponse;
import com.minsoo.co.tireerpserver.model.entity.entities.tire.Tire;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class TireResponse {

    private Long tireId;

    private String productId;

    private PatternResponse pattern;

    private Integer width;

    private Integer flatnessRatio;

    private Integer inch;

    private String size;

    private Integer loadIndex;

    private String speedIndex;

    private Integer price;

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
        this.width = tire.getWidth();
        this.flatnessRatio = tire.getFlatnessRatio();
        this.inch = tire.getInch();
        this.size = tire.getSize();
        this.loadIndex = tire.getLoadIndex();
        this.speedIndex = tire.getSpeedIndex();
        this.price = tire.getPrice();
        this.options = tire.getOptions();
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
