package com.minsoo.co.tireerpserver.services.tire.entity;

import com.minsoo.co.tireerpserver.api.v1.model.dto.tire.TireRequest;
import com.minsoo.co.tireerpserver.services.management.entity.Pattern;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "tire", uniqueConstraints = {@UniqueConstraint(name = "tire_unique_tire_identification", columnNames = {"tire_identification"})})
public class Tire {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "tire_id", nullable = false, length = 20)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "pattern_id", nullable = false)
    private Pattern pattern;

    @Column(name = "tire_identification", nullable = false)
    private String tireIdentification;

    @Column(name = "on_sale")
    private Boolean onSale;

    @Column(name = "width", nullable = false)
    private Integer width;

    @Column(name = "flatness_ratio", nullable = false)
    private Integer flatnessRatio;

    @Column(name = "inch", nullable = false)
    private Integer inch;

    // width + "/" + flatnessRatio + "R" + inch
    @Column(name = "size", nullable = false)
    private String size;

    @Column(name = "load_index")
    private Integer loadIndex;

    @Column(name = "speed_index")
    private String speedIndex;

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

    @OneToMany(mappedBy = "tire", fetch = LAZY, cascade = ALL, orphanRemoval = true)
    private final Set<TireDot> tireDots = new HashSet<>();

    @OneToMany(mappedBy = "tire", fetch = LAZY, cascade = ALL, orphanRemoval = true)
    private final Set<TireMemo> tireMemos = new HashSet<>();

    //== Business ==//
    public Tire(TireRequest tireRequest, Pattern pattern) {
        this.onSale = tireRequest.getOnSale();
        this.tireIdentification = tireRequest.getTireIdentification();
        this.pattern = pattern;
        this.width = tireRequest.getWidth();
        this.flatnessRatio = tireRequest.getFlatnessRatio();
        this.inch = tireRequest.getInch();
        this.size = tireRequest.getWidth() + "/" + tireRequest.getFlatnessRatio() + "R" + tireRequest.getInch();
        this.loadIndex = tireRequest.getLoadIndex();
        this.speedIndex = tireRequest.getSpeedIndex();
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
    }

    public static Tire of(TireRequest tireRequest, Pattern pattern) {
        return new Tire(tireRequest, pattern);
    }

    public Tire update(TireRequest tireRequest, Pattern pattern) {
        this.onSale = tireRequest.getOnSale();
        this.tireIdentification = tireRequest.getTireIdentification();
        this.pattern = pattern;
        this.width = tireRequest.getWidth();
        this.flatnessRatio = tireRequest.getFlatnessRatio();
        this.inch = tireRequest.getInch();
        this.size = tireRequest.getWidth() + "/" + tireRequest.getFlatnessRatio() + "R" + tireRequest.getInch();
        this.loadIndex = tireRequest.getLoadIndex();
        this.speedIndex = tireRequest.getSpeedIndex();
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

        return this;
    }
}
