package com.minsoo.co.tireerpserver.model.dto.tire.tire;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TireRequest {

    private Boolean onSale;

    private String productId;

    private Long patternId;

    private Integer width;

    private Integer flatnessRatio;

    private Integer inch;

    private Integer loadIndex;

    private String speedIndex;

    @Schema(description = "런플렛")
    private Boolean runFlat;

    @Schema(description = "스펀지")
    private Boolean sponge;

    @Schema(description = "실링")
    private Boolean sealing;

    private String oe;

    private String countryOfManufacture;

    private String originalVehicle;

    private String note;

    private String group;

    private String pr;

    private String lr;
}
