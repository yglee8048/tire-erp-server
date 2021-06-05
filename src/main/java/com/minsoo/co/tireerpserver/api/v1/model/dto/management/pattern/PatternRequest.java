package com.minsoo.co.tireerpserver.api.v1.model.dto.management.pattern;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatternRequest {

    @Schema(name = "name", description = "패턴 이름")
    @NotEmpty(message = "패턴 이름은 필수 값입니다.")
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
}
