package com.minsoo.co.tireerpserver.entity.tire;

import com.minsoo.co.tireerpserver.entity.BaseEntity;
import com.minsoo.co.tireerpserver.entity.management.Pattern;
import com.minsoo.co.tireerpserver.model.request.tire.TireRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tire")
public class Tire extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tire_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pattern_id")
    private Pattern pattern;

    @Column(name = "tire_code")
    private String tireCode;

    @Column(name = "on_sale")
    private Boolean onSale;

    @Column(name = "width")
    private Integer width;

    @Column(name = "flatness_ratio")
    private Integer flatnessRatio;

    @Column(name = "inch")
    private Integer inch;

    // width + "/" + flatnessRatio + "R" + inch
    @Column(name = "size")
    private String size;

    @Column(name = "load_index")
    private Integer loadIndex;

    @Column(name = "speed_index")
    private String speedIndex;

    @Column(name = "retail_price")
    private Long retailPrice;

    @Column(name = "run_flat")
    private Boolean runFlat;

    @Column(name = "sponge")
    private Boolean sponge;

    @Column(name = "sealing")
    private Boolean sealing;

    @Column(name = "oe")
    private String oe;

    @Column(name = "country_of_manufacture")
    private String countryOfManufacture;

    @Column(name = "original_vehicle")
    private String originalVehicle;

    @Column(name = "note")
    private String note;

    @Column(name = "tire_group")
    private String tireGroup;

    @Column(name = "pr")
    private String pr;

    @Column(name = "lr")
    private String lr;

    @Column(name = "tire_ro_id")
    private String tireRoId;

    @OneToMany(mappedBy = "tire", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<TireDot> tireDots = new HashSet<>();

    public static Tire of(Pattern pattern, TireRequest tireRequest) {
        Tire tire = new Tire();
        return tire.update(pattern, tireRequest);
    }

    public Tire update(Pattern pattern, TireRequest tireRequest) {
        this.pattern = pattern;
        this.tireCode = tireRequest.getTireCode();
        this.onSale = tireRequest.getOnSale();
        this.width = tireRequest.getWidth();
        this.flatnessRatio = tireRequest.getFlatnessRatio();
        this.inch = tireRequest.getInch();
        this.size = String.format("%s/%sR%s", tireRequest.getWidth(), tireRequest.getFlatnessRatio(), tireRequest.getInch());
        this.loadIndex = tireRequest.getLoadIndex();
        this.speedIndex = tireRequest.getSpeedIndex();
        this.retailPrice = tireRequest.getRetailPrice();
        this.runFlat = tireRequest.getRunFlat();
        this.sponge = tireRequest.getSponge();
        this.sealing = tireRequest.getSealing();
        this.oe = tireRequest.getOe();
        this.countryOfManufacture = tireRequest.getCountryOfManufacture();
        this.originalVehicle = tireRequest.getOriginalVehicle();
        this.note = tireRequest.getNote();
        this.tireGroup = tireRequest.getTireGroup();
        this.pr = tireRequest.getPr();
        this.lr = tireRequest.getLr();
        this.tireRoId = tireRequest.getTireRoId();
        return this;
    }
}
