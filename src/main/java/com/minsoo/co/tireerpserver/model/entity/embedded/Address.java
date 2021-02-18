package com.minsoo.co.tireerpserver.model.entity.embedded;

import com.minsoo.co.tireerpserver.model.dto.general.AddressDTO;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import static lombok.AccessLevel.*;

@Getter
@NoArgsConstructor(access = PROTECTED)
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
    private Integer zipCode;

    private Address(AddressDTO addressDTO) {
        this.city = addressDTO.getCity() == null ? "" : addressDTO.getCity();   // embedded 타입의 경우, 모두 null 이면 에러 발생
        this.streetAddress = addressDTO.getStreetAddress();
        this.detailAddress = addressDTO.getDetailAddress();
        this.zipCode = addressDTO.getZipCode();
    }

    public static Address of(AddressDTO addressDTO) {
        return new Address(addressDTO);
    }
}
