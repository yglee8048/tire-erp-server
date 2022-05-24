package com.minsoo.co.tireerpserver.entity;

import com.minsoo.co.tireerpserver.model.AddressDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Address {

    // 도시
    @Column(name = "city")
    private String city;

    // 도로명 주소
    @Column(name = "street_address")
    private String streetAddress;

    // 상세 주소
    @Column(name = "detail_address")
    private String detailAddress;

    // 우편번호
    @Column(name = "zip_code")
    private String zipCode;

    public Address(AddressDTO addressDTO) {
        if (addressDTO == null) {
            return;
        }

        this.city = addressDTO.getCity();
        this.streetAddress = addressDTO.getStreetAddress();
        this.detailAddress = addressDTO.getDetailAddress();
        this.zipCode = addressDTO.getZipCode();
    }
}
