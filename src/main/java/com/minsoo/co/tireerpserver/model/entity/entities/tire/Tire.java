package com.minsoo.co.tireerpserver.model.entity.entities.tire;

import com.minsoo.co.tireerpserver.model.code.TireOption;
import com.minsoo.co.tireerpserver.model.dto.tire.tire.TireRequest;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.*;
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

    @Column(name = "product_id", nullable = false, columnDefinition = "타이어 ID")
    private String productId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "pattern_id", nullable = false, columnDefinition = "패턴 정보")
    private Pattern pattern;

    @Column(name = "width", nullable = false, columnDefinition = "단면폭")
    private Integer width;

    @Column(name = "flatness_ratio", nullable = false, columnDefinition = "편평비")
    private Integer flatnessRatio;

    @Column(name = "inch", nullable = false, columnDefinition = "인치")
    private Integer inch;

    @Column(name = "size", nullable = false, columnDefinition = "사이즈(단면폭 + '/' + 편평비 + 'R' + 인치)")
    private String size;

    @Column(name = "load_index", columnDefinition = "하중 지수")
    private Integer loadIndex;

    @Column(name = "speed_index", columnDefinition = "속도 지수")
    private String speedIndex;

    @Enumerated(STRING)
    @ElementCollection(fetch = EAGER)
    @CollectionTable(name = "tire_options", joinColumns = @JoinColumn(name = "tire_id", referencedColumnName = "tire_id"))
    @Column(name = "option", columnDefinition = "옵션 정보(런플렛, 스펀지, 실링)")
    private final List<TireOption> options = new ArrayList<>();

    @Column(name = "oe", columnDefinition = "OE 마크")
    private String oe;

    @Column(name = "country_of_manufacture", columnDefinition = "제조국")
    private String countryOfManufacture;

    @Column(name = "original_vehicle", columnDefinition = "순정 차량")
    private String originalVehicle;

    @Column(name = "note", columnDefinition = "비고")
    private String note;

    @Column(name = "group", columnDefinition = "그룹")
    private String group;

    @Column(name = "pr", columnDefinition = "PR")
    private String pr;

    @Column(name = "lr", columnDefinition = "L.R")
    private String lr;

    @OneToMany(mappedBy = "tire", fetch = LAZY, cascade = ALL, orphanRemoval = true)
    private final Set<TireDot> tireDots = new HashSet<>();

    @OneToMany(mappedBy = "tire", fetch = LAZY, cascade = ALL, orphanRemoval = true)
    private final Set<TireMemo> tireMemos = new HashSet<>();

    //== Business ==//
    public Tire(TireRequest tireRequest, Pattern pattern) {
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
