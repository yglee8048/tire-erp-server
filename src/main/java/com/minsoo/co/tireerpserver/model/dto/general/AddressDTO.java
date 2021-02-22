package com.minsoo.co.tireerpserver.model.dto.general;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minsoo.co.tireerpserver.model.entity.embedded.Address;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Positive;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

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

    private AddressDTO(Address address) {
        this.city = address.getCity();
        this.streetAddress = address.getStreetAddress();
        this.detailAddress = address.getDetailAddress();
        this.zipCode = address.getZipCode();
    }

    public static AddressDTO of(Address address) {
        return new AddressDTO(address);
    }
}
