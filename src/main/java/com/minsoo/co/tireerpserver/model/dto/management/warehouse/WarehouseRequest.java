package com.minsoo.co.tireerpserver.model.dto.management.warehouse;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minsoo.co.tireerpserver.model.dto.general.AddressDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import static lombok.AccessLevel.PROTECTED;

@Getter
@ToString
@NoArgsConstructor(access = PROTECTED)
public class WarehouseRequest {

    @ApiModelProperty(value = "이름", example = "왕십리 메인 창고")
    @NotNull(message = "창고 이름은 필수 값입니다.")
    @JsonProperty("name")
    private String name;

    @ApiModelProperty(value = "설명", example = "메인 판매 타이어 보관")
    @JsonProperty("description")
    private String description;

    @ApiModelProperty(value = "용량", example = "20000")
    @Positive(message = "창고 용량은 양수여야 합니다.")
    @JsonProperty("capacity")
    private Integer capacity;

    @ApiModelProperty("주소")
    private AddressDTO address;
}
