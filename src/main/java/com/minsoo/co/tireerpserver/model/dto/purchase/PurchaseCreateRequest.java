package com.minsoo.co.tireerpserver.model.dto.purchase;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseCreateRequest {

    @ApiModelProperty(value = "매입처 ID", example = "20019", required = true)
    @NotNull(message = "매입처 ID 는 필수 값입니다.")
    private Long vendorId;

    @ApiModelProperty(value = "매입 일자", example = "2021-02-18", required = true)
    @NotNull(message = "매입 일자는 필수 값입니다.")
    private LocalDate purchaseDate;

    @ApiModelProperty(value = "매입 내용", required = true)
    @NotNull(message = "매입 내용은 필수 값입니다.")
    List<PurchaseCreateRequestContent> contents;
}
