package com.minsoo.co.tireerpserver.model;

import com.minsoo.co.tireerpserver.constant.PatternSeason;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TireInfoResponse {

    private Long tireId;

    private Long brandId;
    private String brandName;

    private Long patternId;
    private String patternName;
    private String patternEnglishName;

    private Integer width;
    private String flatnessRatio;
    private Integer inch;
    private String size;
    private String oe;
    private Integer loadIndex;
    private String speedIndex;

    private Boolean runFlat;
    private Boolean sponge;
    private Boolean sealing;

    private Long factoryPrice;
    private String countryOfManufacture;

    private PatternSeason season;
    private Boolean quietness;
    private Boolean rideQuality;
    private Boolean mileage;
    private Boolean handling;
    private Boolean breakingPower;
    private Boolean wetSurface;
    private Boolean snowPerformance;

    private String tireCode;

    private String createdBy;
    private LocalDateTime createdAt;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedAt;
}
