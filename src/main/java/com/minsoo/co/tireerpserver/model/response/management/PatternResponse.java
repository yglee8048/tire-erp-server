package com.minsoo.co.tireerpserver.model.response.management;

import com.minsoo.co.tireerpserver.entity.management.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PatternResponse {

    private Long patternId;
    private Long brandId;
    private String name;
    private String patternCode;
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

    private String createdBy;
    private LocalDateTime createdAt;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedAt;

    public PatternResponse(Pattern pattern) {
        this.patternId = pattern.getId();
        this.brandId = pattern.getBrand().getId();
        this.name = pattern.getName();
        this.patternCode = pattern.getPatternCode();
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

        this.createdAt = pattern.getCreatedAt();
        this.lastModifiedAt = pattern.getLastModifiedAt();
        this.createdBy = pattern.getCreatedBy();
        this.lastModifiedBy = pattern.getLastModifiedBy();
    }
}
