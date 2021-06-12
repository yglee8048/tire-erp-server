package com.minsoo.co.tireerpserver.management.model.pattern;

import com.minsoo.co.tireerpserver.management.model.brand.BrandResponse;
import com.minsoo.co.tireerpserver.management.entity.Pattern;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
public class PatternResponse {

    @Schema(name = "pattern_id", description = "pattern_id")
    private Long patternId;

    @Schema(name = "brand", description = "제조사 정보")
    private BrandResponse brand;

    @Schema(name = "name", description = "패턴 이름")
    private String name;

    @Schema(name = "car_type", description = "차종")
    private String carType;

    @Schema(name = "rank", description = "등급")
    private String rank;

    @Schema(name = "season", description = "계절")
    private String season;

    @Schema(name = "quietness", description = "정숙성")
    private Boolean quietness;

    @Schema(name = "ride_quality", description = "승차감")
    private Boolean rideQuality;

    @Schema(name = "mileage", description = "마일리지")
    private Boolean mileage;

    @Schema(name = "handling", description = "핸들링")
    private Boolean handling;

    @Schema(name = "breaking_power", description = "제동력")
    private Boolean breakingPower;

    @Schema(name = "sports", description = "스포츠")
    private Boolean sports;

    @Schema(name = "wet_surface", description = "젖은노면")
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