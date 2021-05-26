package com.minsoo.co.tireerpserver.model.dto.management.pattern;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatternRequest {

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
}
