package com.minsoo.co.tireerpserver.model.entity.entities.tire;

import com.minsoo.co.tireerpserver.model.dto.tire.tire.TireRequest;
import com.minsoo.co.tireerpserver.model.entity.entities.management.Pattern;
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
@Table(name = "tire", uniqueConstraints = {@UniqueConstraint(name = "tire_unique_product_id", columnNames = {"product_id"})})
public class Tire {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "tire_id", nullable = false, length = 20)
    private Long id;

    @Column(name = "on_sale")
    private Boolean onSale;

    @Column(name = "product_id", nullable = false)
    private String productId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "pattern_id", nullable = false)
    private Pattern pattern;

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

    // 옵션 정보(런플렛, 스펀지, 실링)
    @OneToMany(mappedBy = "tire", fetch = LAZY, cascade = ALL, orphanRemoval = true)
    private final Set<TireOptions> options = new HashSet<>();

    @Column(name = "oe")
    private String oe;

    @Column(name = "country_of_manufacture")
    private String countryOfManufacture;

    @Column(name = "original_vehicle")
    private String originalVehicle;

    @Column(name = "note")
    private String note;

    @Column(name = "group")
    private String group;

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
        this.productId = tireRequest.getProductId();
        this.pattern = pattern;
        this.width = tireRequest.getWidth();
        this.flatnessRatio = tireRequest.getFlatnessRatio();
        this.inch = tireRequest.getInch();
        this.size = tireRequest.getWidth() + "/" + tireRequest.getFlatnessRatio() + "R" + tireRequest.getInch();
        this.loadIndex = tireRequest.getLoadIndex();
        this.speedIndex = tireRequest.getSpeedIndex();
        this.oe = tireRequest.getOe();
        this.countryOfManufacture = tireRequest.getCountryOfManufacture();
        this.originalVehicle = tireRequest.getOriginalVehicle();
        this.note = tireRequest.getNote();
        this.group = tireRequest.getGroup();
        this.pr = tireRequest.getPr();
        this.lr = tireRequest.getLr();
    }

    public static Tire of(TireRequest tireRequest, Pattern pattern) {
        return new Tire(tireRequest, pattern);
    }

    public Tire update(TireRequest tireRequest, Pattern pattern) {
        this.onSale = tireRequest.getOnSale();
        this.productId = tireRequest.getProductId();
        this.pattern = pattern;
        this.width = tireRequest.getWidth();
        this.flatnessRatio = tireRequest.getFlatnessRatio();
        this.inch = tireRequest.getInch();
        this.size = tireRequest.getWidth() + "/" + tireRequest.getFlatnessRatio() + "R" + tireRequest.getInch();
        this.loadIndex = tireRequest.getLoadIndex();
        this.speedIndex = tireRequest.getSpeedIndex();
        this.oe = tireRequest.getOe();
        this.countryOfManufacture = tireRequest.getCountryOfManufacture();
        this.originalVehicle = tireRequest.getOriginalVehicle();
        this.note = tireRequest.getNote();
        this.group = tireRequest.getGroup();
        this.pr = tireRequest.getPr();
        this.lr = tireRequest.getLr();

        return this;
    }
}
