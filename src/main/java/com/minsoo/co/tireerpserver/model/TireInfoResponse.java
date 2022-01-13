package com.minsoo.co.tireerpserver.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TireInfoResponse {

    private Long tireId;
    private String tireCode;
    private Long brandId;
    private String brandName;
    private Boolean onSale;
    private Integer width;
    private Integer flatnessRatio;
    private Integer inch;
    private String size;
    private Integer loadIndex;
    private String speedIndex;
    private Long patternId;
    private String patternName;
    private Boolean runFlat;
    private Boolean sponge;
    private Boolean sealing;
    private String oe;
    private String originalVehicle;
    private String countryOfManufacture;
    private Long retailPrice;

    private String tireGroup;
    private String note;
    private String season;
    private Boolean quietness;
    private Boolean rideQuality;
    private Boolean mileage;
    private Boolean handling;
    private Boolean breakingPower;
    private Boolean sports;
    private Boolean wetSurface;
    private String pr;
    private String lr;
    private String tireRoId;

    private String createdBy;
    private LocalDateTime createdAt;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedAt;
}
