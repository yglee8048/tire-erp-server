package com.minsoo.co.tireerpserver.entity.tire;

import com.minsoo.co.tireerpserver.entity.BaseEntity;
import com.minsoo.co.tireerpserver.entity.management.Pattern;
import com.minsoo.co.tireerpserver.model.request.tire.TireRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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

    @Column(name = "width")
    private Integer width;

    @Column(name = "flatness_ratio")
    private Integer flatnessRatio;

    @Column(name = "inch")
    private Integer inch;

    @Column(name = "size")
    private String size;

    @Column(name = "oe")
    private String oe;

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

    @Column(name = "factory_price")
    private Long factoryPrice;

    @Column(name = "country_of_manufacture")
    private String countryOfManufacture;

    @OneToMany(mappedBy = "tire", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<TireDot> tireDots = new HashSet<>();

    public static Tire of(Pattern pattern, TireRequest tireRequest) {
        Tire tire = new Tire();
        return tire.update(pattern, tireRequest);
    }

    public Tire update(Pattern pattern, TireRequest tireRequest) {
        this.pattern = pattern;
        this.tireCode = tireRequest.getTireCode();
        this.width = tireRequest.getWidth();
        this.flatnessRatio = tireRequest.getFlatnessRatio();
        this.inch = tireRequest.getInch();
        this.size = tireRequest.getSize();
        this.oe = tireRequest.getOe();
        this.loadIndex = tireRequest.getLoadIndex();
        this.speedIndex = tireRequest.getSpeedIndex();
        this.runFlat = tireRequest.getRunFlat();
        this.sponge = tireRequest.getSponge();
        this.sealing = tireRequest.getSealing();
        this.factoryPrice = tireRequest.getFactoryPrice();
        this.countryOfManufacture = tireRequest.getCountryOfManufacture();
        return this;
    }
}
