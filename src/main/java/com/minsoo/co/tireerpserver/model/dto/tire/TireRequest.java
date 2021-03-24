package com.minsoo.co.tireerpserver.model.dto.tire;

import com.minsoo.co.tireerpserver.model.code.TireOption;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(value = "브랜드 ID", example = "2991", required = true)
    @NotNull(message = "브랜드 ID는 필수 값입니다.")
    private Long brandId;

    @ApiModelProperty(value = "상품 ID", example = "P2454518Z", required = true)
    @NotEmpty(message = "상품 ID는 필수 값입니다.")
    private String productId;

    @ApiModelProperty(value = "레이블", example = "어슐런스 듀라플러스")
    private String label;

    @ApiModelProperty(value = "사이즈", example = "1656014", required = true)
    @NotNull(message = "사이즈는 필수 값입니다.")
    private String size;

    @ApiModelProperty(value = "패턴", example = "Pzero", required = true)
    @NotEmpty(message = "패턴은 필수 값입니다.")
    private String pattern;

    @ApiModelProperty(value = "하중지수", example = "79")
    @Positive(message = "하중지수는 양수여야 합니다.")
    private Integer loadIndex;

    @ApiModelProperty(value = "속도지수", example = "H")
    private String speedIndex;

    @ApiModelProperty(value = "계절", example = "3계절")
    private String season;

    @ApiModelProperty(value = "판매가", example = "300000")
    @Positive(message = "판매가는 양수여야 합니다.")
    private Integer price;

    @ApiModelProperty(value = "런플렛", example = "true")
    private Boolean runFlat;

    @ApiModelProperty(value = "옵션", example = "SPONGE")
    private TireOption option;

    @ApiModelProperty(value = "OE", example = "AO")
    private String oe;
}
