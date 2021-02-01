package com.minsoo.co.tireerpserver.model.entity.embedded;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import static lombok.AccessLevel.*;

@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
@ToString
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
}
