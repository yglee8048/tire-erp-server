package com.minsoo.co.tireerpserver.model;

import com.minsoo.co.tireerpserver.entity.Address;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddressDTO {

    @Schema(name = "city", description = "시/도")
    private String city;
    @Schema(name = "street_address", description = "도로명 주소")
    private String streetAddress;
    @Schema(name = "detail_address", description = "세부 주소")
    private String detailAddress;
    @Schema(name = "zip_code", description = "우편번호")
    private Integer zipCode;

    public AddressDTO(Address address) {
        if (address == null) {
            return;
        }

        this.city = address.getCity();
        this.streetAddress = address.getStreetAddress();
        this.detailAddress = address.getDetailAddress();
        this.zipCode = address.getZipCode();
    }
}
