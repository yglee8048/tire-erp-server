package com.minsoo.co.tireerpserver.model.request.tire;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TireRequest {

    @Schema(name = "pattern_id", description = "pattern_id")
    @NotNull(message = "pattern_id 는 필수 값입니다.")
    private Long patternId;

    @Schema(name = "tire_code", description = "타이어 ID")
    @NotEmpty(message = "타이어 ID 는 필수 값입니다.")
    private String tireCode;

    @Schema(name = "width", description = "단면폭")
    @NotNull(message = "단면폭은 필수 값입니다.")
    @Positive(message = "단면폭은 양수여야 합니다.")
    @Max(value = 999, message = "단면폭은 3자리 숫자여야 합니다.")
    @Min(value = 100, message = "단면폭은 3자리 숫자여야 합니다.")
    private Integer width;

    @Schema(name = "flatness_ratio", description = "편평비")
    @NotEmpty(message = "편평비는 필수 값입니다.")
    private String flatnessRatio;

    @Schema(name = "inch", description = "인치")
    @NotNull(message = "인치는 필수 값입니다.")
    @Positive(message = "인치는 양수여야 합니다.")
    @Max(value = 99, message = "인치는 2자리 숫자여야 합니다.")
    @Min(value = 10, message = "인치는 2자리 숫자여야 합니다.")
    private Integer inch;

    @Schema(name = "size", description = "사이즈")
    @NotEmpty(message = "사이즈는 필수 값입니다.")
    private String size;

    @Schema(name = "oe", description = "OE 마크")
    private String oe;

    @Schema(name = "load_index", description = "하중지수")
    @Positive(message = "하중지수는 양수여야 합니다.")
    private Integer loadIndex;

    @Schema(name = "speed_index", description = "속도지수")
    private String speedIndex;

    @Schema(name = "run_flat", description = "런플렛")
    private Boolean runFlat;

    @Schema(name = "sponge", description = "스펀지")
    private Boolean sponge;

    @Schema(name = "sealing", description = "실링")
    private Boolean sealing;

    @Schema(name = "factory_price", description = "공장도가")
    @Positive(message = "공장도가는 양수여야 합니다.")
    private Long factoryPrice;

    @Schema(name = "country_of_manufacture", description = "제조국")
    private String countryOfManufacture;
}
