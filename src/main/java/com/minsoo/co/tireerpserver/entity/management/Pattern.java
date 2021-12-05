package com.minsoo.co.tireerpserver.entity.management;

import com.minsoo.co.tireerpserver.entity.BaseTimeEntity;
import com.minsoo.co.tireerpserver.model.management.request.PatternRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "pattern")
public class Pattern extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pattern_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @Column(name = "name")
    private String name;

    @Column(name = "car_type")
    private String carType;

    @Column(name = "rank")
    private String rank;

    @Column(name = "season")
    private String season;

    @Column(name = "quietness")
    private Boolean quietness;

    @Column(name = "ride_quality")
    private Boolean rideQuality;

    @Column(name = "mileage")
    private Boolean mileage;

    @Column(name = "handling")
    private Boolean handling;

    @Column(name = "breaking_power")
    private Boolean breakingPower;

    @Column(name = "sports")
    private Boolean sports;

    @Column(name = "wet_surface")
    private Boolean wetSurface;

    public static Pattern of(Brand brand, PatternRequest request) {
        Pattern pattern = new Pattern();
        return pattern.update(brand, request);
    }

    public Pattern update(Brand brand, PatternRequest request) {
        this.brand = brand;
        this.name = request.getName();
        this.carType = request.getCarType();
        this.rank = request.getRank();
        this.season = request.getSeason();
        this.quietness = request.getQuietness();
        this.rideQuality = request.getRideQuality();
        this.mileage = request.getMileage();
        this.handling = request.getHandling();
        this.breakingPower = request.getBreakingPower();
        this.sports = request.getSports();
        this.wetSurface = request.getWetSurface();
        return this;
    }
}
