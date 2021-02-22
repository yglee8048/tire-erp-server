package com.minsoo.co.tireerpserver.model.dto.tire;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minsoo.co.tireerpserver.model.code.TireOption;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TireRequest {

    @ApiModelProperty(value = "브랜드 ID", example = "2991")
    @NotNull(message = "브랜드 ID는 필수 값입니다.")
    @JsonProperty("brand_id")
    private Long brandId;

    @ApiModelProperty(value = "상품 ID", example = "P2454518Z")
    @NotNull(message = "상품 ID는 필수 값입니다.")
    @JsonProperty("product_id")
    private String productId;

    @ApiModelProperty(value = "레이블", example = "어슐런스 듀라플러스")
    @JsonProperty("label")
    private String label;

    @ApiModelProperty(value = "단면폭", example = "165")
    @NotNull(message = "단면폭은 필수 값입니다.")
    @Positive(message = "단면폭은 양수여야 합니다.")
    @JsonProperty("width")
    private Integer width;

    @ApiModelProperty(value = "편평비", example = "60")
    @NotNull(message = "편평비는 필수 값입니다.")
    @Positive(message = "편평비는 양수여야 합니다.")
    @JsonProperty("flatness_ratio")
    private Integer flatnessRatio;

    @ApiModelProperty(value = "인치", example = "14")
    @NotNull(message = "인치는 필수 값입니다.")
    @Positive(message = "인치는 양수여야 합니다.")
    @JsonProperty("inch")
    private Integer inch;

    @ApiModelProperty(value = "패턴", example = "Pzero")
    @NotNull(message = "패턴은 필수 값입니다.")
    @JsonProperty("pattern")
    private String pattern;

    @ApiModelProperty(value = "하중지수", example = "79")
    @Positive(message = "하중지수는 양수여야 합니다.")
    @JsonProperty("load_index")
    private Integer loadIndex;

    @ApiModelProperty(value = "속도지수", example = "H")
    @JsonProperty("speed_index")
    private String speedIndex;

    @ApiModelProperty(value = "계절", example = "3계절")
    @JsonProperty("season")
    private String season;

    @ApiModelProperty(value = "판매가", example = "300000")
    @Positive(message = "판매가는 양수여야 합니다.")
    @JsonProperty("price")
    private Integer price;

    @ApiModelProperty(value = "런플렛", example = "true")
    @JsonProperty("run_flat")
    private boolean runFlat;

    @ApiModelProperty(value = "옵션", example = "SPONGE")
    @JsonProperty("option")
    private TireOption option;

    @ApiModelProperty(value = "OE", example = "AO")
    @JsonProperty("oe")
    private String oe;
}
