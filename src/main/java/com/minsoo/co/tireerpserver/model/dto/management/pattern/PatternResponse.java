package com.minsoo.co.tireerpserver.model.dto.management.pattern;

import com.minsoo.co.tireerpserver.model.dto.management.brand.BrandResponse;
import com.minsoo.co.tireerpserver.model.entity.entities.management.Pattern;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
public class PatternResponse {

    private Long patternId;

    private BrandResponse brand;

    private String name;

    private String carType;

    private String rank;

    private String season;

    @Schema(description = "정숙성")
    private Boolean quietness;

    @Schema(description = "승차감")
    private Boolean rideQuality;

    @Schema(description = "마일리지")
    private Boolean mileage;

    @Schema(description = "핸들링")
    private Boolean handling;

    @Schema(description = "제동력")
    private Boolean breakingPower;

    @Schema(description = "스포츠")
    private Boolean sports;

    @Schema(description = "젖은노면")
    private Boolean wetSurface;

    public PatternResponse(Pattern pattern) {
        this.patternId = pattern.getId();
        this.brand = BrandResponse.of(pattern.getBrand());
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

    public static PatternResponse of(Pattern pattern) {
        return new PatternResponse(pattern);
    }
}