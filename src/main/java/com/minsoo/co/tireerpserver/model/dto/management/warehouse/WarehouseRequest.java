package com.minsoo.co.tireerpserver.model.dto.management.warehouse;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class WarehouseRequest {

    @ApiModelProperty(value = "이름", example = "왕십리 메인 창고")
    @NotNull(message = "창고 이름은 필수 값입니다.")
    @JsonProperty("name")
    private String name;

    @ApiModelProperty(value = "설명", example = "메인 판매 타이어 보관")
    @JsonProperty("description")
    private String description;

    @ApiModelProperty(value = "이름", example = "왕십리 메인 창고")
    @Positive(message = "창고 용량은 양수여야 합니다.")
    @JsonProperty("capacity")
    private Integer capacity;

    @ApiModelProperty(value = "시/도", example = "서울특별시")
    @JsonProperty("city")
    private String city;

    @ApiModelProperty(value = "도로명 주소", example = "서울특별시 종로구 세종대로 209")
    @JsonProperty("street_address")
    private String streetAddress;

    @ApiModelProperty(value = "상세 주소", example = "1403호")
    @JsonProperty("detail_address")
    private String detailAddress;

    @ApiModelProperty(value = "우편번호", example = "03139")
    @Positive(message = "우편번호는 양수여야 합니다.")
    @JsonProperty("zip_code")
    private Integer zipCode;
}
