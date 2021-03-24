package com.minsoo.co.tireerpserver.model.dto.management.warehouse;

import com.minsoo.co.tireerpserver.model.dto.general.AddressDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseRequest {

    @ApiModelProperty(value = "이름", example = "왕십리 메인 창고", required = true)
    @NotEmpty(message = "창고 이름은 필수 값입니다.")
    private String name;

    @ApiModelProperty(value = "설명", example = "메인 판매 타이어 보관")
    private String description;

    @ApiModelProperty(value = "용량", example = "20000")
    @Positive(message = "창고 용량은 양수여야 합니다.")
    private Integer capacity;

    @ApiModelProperty("주소")
    private AddressDTO address;
}
