package com.minsoo.co.tireerpserver.model.entity.embedded;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@Embeddable
public class BusinessInfo {

    @Column(name = "business_number")
    private String businessNumber;

    @Column(name = "business_name")
    private String businessName;

    @Column(name = "business_type")
    private String businessType;

    @Embedded
    private Address address;

    @Column(name = "fax")
    private String fax;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "representative")
    private String representative;

    @Column(name = "representative_phone_number")
    private String representativePhoneNumber;

    @Column(name = "manager")
    private String manager;

    @Column(name = "manager_phone_number")
    private String managerPhoneNumber;
}
