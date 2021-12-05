package com.minsoo.co.tireerpserver.model;

import com.minsoo.co.tireerpserver.entity.Address;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddressDTO {

    private String city;
    private String streetAddress;
    private String detailAddress;
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
