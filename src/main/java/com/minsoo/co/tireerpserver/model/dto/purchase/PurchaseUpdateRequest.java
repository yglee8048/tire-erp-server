package com.minsoo.co.tireerpserver.model.dto.purchase;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseUpdateRequest {

    @ApiModelProperty(value = "매입처 ID", example = "20019", required = true)
    @NotNull(message = "매입처 ID 는 필수 값입니다.")
    private Long vendorId;

    @ApiModelProperty(value = "매입 일자", example = "2021-02-18", required = true)
    @NotNull(message = "매입 일자는 필수 값입니다.")
    private LocalDate purchaseDate;

    @ApiModelProperty(value = "타이어 ID", example = "20019", required = true)
    @NotNull(message = "타이어 ID 는 필수 값입니다.")
    private Long tireId;

    @ApiModelProperty(value = "DOT", example = "1223", required = true)
    @NotEmpty(message = "DOT 는 필수 값입니다.")
    private String dot;

    @ApiModelProperty(value = "창고 ID", example = "20019", required = true)
    @NotNull(message = "창고 ID 는 필수 값입니다.")
    private Long warehouseId;

    @ApiModelProperty(value = "매입 가격", example = "450000", required = true)
    @NotNull(message = "매입 가격은 필수 값입니다.")
    @Positive(message = "매입 가격은 양수여야 합니다.")
    private Integer price;

    @ApiModelProperty(value = "매입 수량", example = "45", required = true)
    @NotNull(message = "매입 수량은 필수 값입니다.")
    @Positive(message = "매입 수량은 양수여야 합니다.")
    private Long quantity;
}
