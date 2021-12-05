package com.minsoo.co.tireerpserver.model.management.response;

import com.minsoo.co.tireerpserver.entity.management.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PatternResponse {

    private Long id;
    private BrandResponse brand;
    private String name;
    private String carType;
    private String rank;
    private String season;
    private Boolean quietness;
    private Boolean rideQuality;
    private Boolean mileage;
    private Boolean handling;
    private Boolean breakingPower;
    private Boolean sports;
    private Boolean wetSurface;

    public PatternResponse(Pattern pattern) {
        this.id = pattern.getId();
        this.brand = new BrandResponse(pattern.getBrand());
        this.name = pattern.getName();
        this.carType = pattern.getCarType();
        this.rank = pattern.getRank();
        this.season = pattern.getSeason();
        this.quietness = pattern.getQuietness();
        this.rideQuality = pattern.getRideQuality();
        this.mileage = pattern.getMileage();
        this.handling = pattern.getHandling();
        this.breakingPower = pattern.getBreakingPower();
        this.sports = pattern.getSports();
        this.wetSurface = pattern.getWetSurface();
    }
}
