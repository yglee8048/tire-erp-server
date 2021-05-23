package com.minsoo.co.tireerpserver.model.dto.tire.tire;

import com.minsoo.co.tireerpserver.model.code.TireOption;
import com.minsoo.co.tireerpserver.model.dto.management.pattern.PatternResponse;
import com.minsoo.co.tireerpserver.model.dto.tire.dot.TireDotSimpleResponse;
import com.minsoo.co.tireerpserver.model.dto.tire.memo.TireMemoResponse;
import com.minsoo.co.tireerpserver.model.entity.entities.tire.Tire;
import com.minsoo.co.tireerpserver.model.entity.entities.tire.TireOptions;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class TireDetailResponse {

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

    private List<TireOption> options;

    private String oe;

    private String countryOfManufacture;

    private String originalVehicle;

    private String note;

    private String group;

    private String pr;

    private String lr;

    private List<TireDotSimpleResponse> tireDots;

    private List<TireMemoResponse> tireMemos;

    public TireDetailResponse(Tire tire) {
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
        this.tireDots = tire.getTireDots().stream().map(TireDotSimpleResponse::of).collect(Collectors.toList());
        this.tireMemos = tire.getTireMemos().stream().map(TireMemoResponse::of).collect(Collectors.toList());
    }

    public static TireDetailResponse of(Tire tire) {
        return new TireDetailResponse(tire);
    }
}
