package com.minsoo.co.tireerpserver.entity;

import com.minsoo.co.tireerpserver.model.BusinessInfoDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Column(name = "email")
    private String email;

    @Column(name = "representative")
    private String representative;

    @Column(name = "representative_phone_number")
    private String representativePhoneNumber;

    @Column(name = "manager")
    private String manager;

    @Column(name = "manager_phone_number")
    private String managerPhoneNumber;

    public BusinessInfo(BusinessInfoDTO businessInfoDTO) {
        if (businessInfoDTO == null) {
            return;
        }

        this.businessNumber = businessInfoDTO.getBusinessNumber();
        this.businessName = businessInfoDTO.getBusinessName();
        this.businessType = businessInfoDTO.getBusinessType();
        this.address = new Address(businessInfoDTO.getAddress());
        this.fax = businessInfoDTO.getFax();
        this.email = businessInfoDTO.getEmail();
        this.representative = businessInfoDTO.getRepresentative();
        this.representativePhoneNumber = businessInfoDTO.getRepresentativePhoneNumber();
        this.manager = businessInfoDTO.getManager();
        this.managerPhoneNumber = businessInfoDTO.getManagerPhoneNumber();
    }
}
