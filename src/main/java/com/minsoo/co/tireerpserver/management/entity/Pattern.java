package com.minsoo.co.tireerpserver.management.entity;

import com.minsoo.co.tireerpserver.management.model.pattern.PatternRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "pattern", uniqueConstraints = {@UniqueConstraint(name = "pattern_unique_name", columnNames = {"brand_id", "name"})})
public class Pattern {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "pattern_id", nullable = false, length = 20)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @Column(name = "name", nullable = false)
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

    //== Business ==//
    public Pattern(PatternRequest patternRequest, Brand brand) {
        this.brand = brand;
        this.name = patternRequest.getName();
        this.carType = patternRequest.getCarType();
        this.rank = patternRequest.getRank();
        this.season = patternRequest.getSeason();
        this.quietness = patternRequest.getQuietness();
        this.rideQuality = patternRequest.getRideQuality();
        this.mileage = patternRequest.getMileage();
        this.handling = patternRequest.getHandling();
        this.breakingPower = patternRequest.getBreakingPower();
        this.sports = patternRequest.getSports();
        this.wetSurface = patternRequest.getWetSurface();
    }

    public static Pattern of(PatternRequest patternRequest, Brand brand) {
        return new Pattern(patternRequest, brand);
    }

    public Pattern update(PatternRequest patternRequest, Brand brand) {
        this.brand = brand;
        this.name = patternRequest.getName();
        this.carType = patternRequest.getCarType();
        this.rank = patternRequest.getRank();
        this.season = patternRequest.getSeason();
        this.quietness = patternRequest.getQuietness();
        this.rideQuality = patternRequest.getRideQuality();
        this.mileage = patternRequest.getMileage();
        this.handling = patternRequest.getHandling();
        this.breakingPower = patternRequest.getBreakingPower();
        this.sports = patternRequest.getSports();
        this.wetSurface = patternRequest.getWetSurface();

        return this;
    }
}