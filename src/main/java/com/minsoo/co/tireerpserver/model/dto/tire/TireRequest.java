package com.minsoo.co.tireerpserver.model.dto.tire;

import com.minsoo.co.tireerpserver.model.code.TireOption;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TireRequest {

    @Schema(name = "브랜드 ID", example = "2991", required = true)
    @NotNull(message = "브랜드 ID는 필수 값입니다.")
    private Long brandId;

    @Schema(name = "상품 ID", example = "P2454518Z", required = true)
    @NotEmpty(message = "상품 ID는 필수 값입니다.")
    private String productId;

    @Schema(name = "레이블", example = "어슐런스 듀라플러스")
    private String label;

    @Schema(name = "단면폭", example = "165", required = true)
    @NotNull(message = "단면폭은 필수 값입니다.")
    @Positive(message = "단면폭은 양수여야 합니다.")
    private Integer width;

    @Schema(name = "편평비", example = "60", required = true)
    @NotNull(message = "편평비는 필수 값입니다.")
    @Positive(message = "편평비는 양수여야 합니다.")
    private Integer flatnessRatio;

    @Schema(name = "인치", example = "14", required = true)
    @NotNull(message = "인치는 필수 값입니다.")
    @Positive(message = "인치는 양수여야 합니다.")
    private Integer inch;

    @Schema(name = "패턴", example = "Pzero", required = true)
    @NotEmpty(message = "패턴은 필수 값입니다.")
    private String pattern;

    @Schema(name = "하중지수", example = "79")
    @Positive(message = "하중지수는 양수여야 합니다.")
    private Integer loadIndex;

    @Schema(name = "속도지수", example = "H")
    private String speedIndex;

    @Schema(name = "계절", example = "3계절")
    private String season;

    @Schema(name = "판매가", example = "300000")
    @Positive(message = "판매가는 양수여야 합니다.")
    private Integer price;

    @Schema(name = "런플렛", example = "true")
    private Boolean runFlat;

    @Schema(name = "옵션", example = "SPONGE")
    private TireOption option;

    @Schema(name = "OE", example = "AO")
    private String oe;
}
