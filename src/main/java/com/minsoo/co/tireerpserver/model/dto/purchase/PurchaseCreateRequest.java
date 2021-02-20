package com.minsoo.co.tireerpserver.model.dto.purchase;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Getter
@ToString
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class PurchaseCreateRequest {

    @ApiModelProperty(value = "매입처 ID", example = "20019")
    @NotNull(message = "매입처 ID 는 필수 값입니다.")
    @JsonProperty("vendor_id")
    private Long vendorId;

    @ApiModelProperty(value = "매입 일자", example = "2021-02-18")
    @NotNull(message = "매입 일자는 필수 값입니다.")
    @JsonProperty("purchased_date")
    private LocalDate purchasedDate;

    @ApiModelProperty(value = "매입 내용")
    @NotNull(message = "매입 내용은 필수 값입니다.")
    @JsonProperty("contents")
    List<PurchaseCreateRequestContent> contents;
}
