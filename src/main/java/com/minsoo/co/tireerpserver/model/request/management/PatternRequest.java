package com.minsoo.co.tireerpserver.model.request.management;

import com.minsoo.co.tireerpserver.constant.PatternSeason;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatternRequest {

    @Schema(name = "name", description = "패턴명")
    @NotEmpty(message = "패턴명은 필수 값입니다.")
    private String name;

    @Schema(name = "english_name", description = "영문 패턴명")
    private String englishName;

    @Schema(name = "description", description = "설명")
    private String description;

    @Schema(name = "season", description = "계절", example = "ALL_SEASON")
    private PatternSeason season;

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

    @Schema(name = "wet_surface", description = "젖은 노면")
    private Boolean wetSurface;

    @Schema(name = "snow_performance", description = "눈길 성능")
    private Boolean snowPerformance;
}
