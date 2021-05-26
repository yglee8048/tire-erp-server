package com.minsoo.co.tireerpserver.model.dto.tire.tire;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TireRequest {

    @Schema(name = "on_sale", description = "판매상태")
    private Boolean onSale;

    @Schema(name = "product_id", description = "타이어 ID")
    @NotEmpty(message = "타이어 ID 는 필수 값입니다.")
    private String productId;

    @Schema(name = "pattern_id", description = "pattern_id")
    @NotNull(message = "pattern_id 는 필수 값입니다.")
    private Long patternId;

    @Schema(name = "width", description = "단면폭")
    @NotNull(message = "단면폭은 필수 값입니다.")
    @Positive(message = "단면폭은 양수여야 합니다.")
    private Integer width;

    @Schema(name = "flatness_ratio", description = "편평비")
    @NotNull(message = "편평비는 필수 값입니다.")
    @Positive(message = "편평비는 양수여야 합니다.")
    private Integer flatnessRatio;

    @Schema(name = "inch", description = "인치")
    @NotNull(message = "인치는 필수 값입니다.")
    @Positive(message = "인치는 양수여야 합니다.")
    private Integer inch;

    @Schema(name = "load_index", description = "하중지수")
    private Integer loadIndex;

    @Schema(name = "speed_index", description = "속도지수")
    private String speedIndex;

    @Schema(name = "run_flat", description = "런플렛")
    private Boolean runFlat;

    @Schema(name = "sponge", description = "스펀지")
    private Boolean sponge;

    @Schema(name = "sealing", description = "실링")
    private Boolean sealing;

    @Schema(name = "oe", description = "OE 마크")
    private String oe;

    @Schema(name = "country_of_manufacture", description = "제조국")
    private String countryOfManufacture;

    @Schema(name = "original_vehicle", description = "순정 차량")
    private String originalVehicle;

    @Schema(name = "note", description = "비고")
    private String note;

    @Schema(name = "group", description = "그룹")
    private String group;

    @Schema(name = "pr", description = "pr")
    private String pr;

    @Schema(name = "lr", description = "lr")
    private String lr;
}
