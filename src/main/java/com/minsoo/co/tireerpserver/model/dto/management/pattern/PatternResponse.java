package com.minsoo.co.tireerpserver.model.dto.management.pattern;

import com.minsoo.co.tireerpserver.model.code.PatternOption;
import com.minsoo.co.tireerpserver.model.dto.management.brand.BrandResponse;
import com.minsoo.co.tireerpserver.model.entity.entities.management.Pattern;
import com.minsoo.co.tireerpserver.model.entity.entities.management.PatternOptions;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

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
    private boolean quietness;

    @Schema(description = "승차감")
    private boolean rideQuality;

    @Schema(description = "마일리지")
    private boolean mileage;

    @Schema(description = "핸들링")
    private boolean handling;

    @Schema(description = "제동력")
    private boolean breakingPower;

    @Schema(description = "스포츠")
    private boolean sports;

    @Schema(description = "젖은노면")
    private boolean wetSurface;

    public PatternResponse(Pattern pattern) {
        this.patternId = pattern.getId();
        this.brand = BrandResponse.of(pattern.getBrand());
        this.name = pattern.getName();
        this.carType = pattern.getCarType();
        this.rank = pattern.getRank();
        this.season = pattern.getSeason();

        List<PatternOption> patternOptions = pattern.getOptions().stream().map(PatternOptions::getOption).collect(Collectors.toList());
        this.quietness = patternOptions.contains(PatternOption.QUIETNESS);
        this.rideQuality = patternOptions.contains(PatternOption.RIDE_QUALITY);
        this.mileage = patternOptions.contains(PatternOption.MILEAGE);
        this.handling = patternOptions.contains(PatternOption.HANDLING);
        this.breakingPower = patternOptions.contains(PatternOption.BREAKING_POWER);
        this.sports = patternOptions.contains(PatternOption.SPORTS);
        this.wetSurface = patternOptions.contains(PatternOption.WET_SURFACE);
    }

    public static PatternResponse of(Pattern pattern) {
        return new PatternResponse(pattern);
    }
}