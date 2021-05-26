package com.minsoo.co.tireerpserver.model.dto.general;

import com.minsoo.co.tireerpserver.model.entity.embedded.Address;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.Positive;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

    @Schema(name = "city", description = "시/도", example = "서울특별시")
    private String city;

    @Schema(name = "street_address", description = "도로명 주소", example = "서울특별시 종로구 세종대로 209")
    private String streetAddress;

    @Schema(name = "detail_address", description = "상세 주소", example = "1403호")
    private String detailAddress;

    @Schema(name = "zip_code", description = "우편번호", example = "03139")
    @Positive(message = "우편번호는 양수여야 합니다.")
    private Integer zipCode;

    private AddressDTO(Address address) {
        this.city = address.getCity();
        this.streetAddress = address.getStreetAddress();
        this.detailAddress = address.getDetailAddress();
        this.zipCode = address.getZipCode();
    }

    public static AddressDTO of(Address address) {
        if (address == null) {
            return new AddressDTO();
        }
        return new AddressDTO(address);
    }
}
