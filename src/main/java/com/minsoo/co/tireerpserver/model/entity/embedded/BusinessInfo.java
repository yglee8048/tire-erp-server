package com.minsoo.co.tireerpserver.model.entity.embedded;

import com.minsoo.co.tireerpserver.model.dto.general.BusinessInfoDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import static lombok.AccessLevel.PROTECTED;

@Getter
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

    private BusinessInfo(BusinessInfoDTO businessInfoDTO) {
        this.businessNumber = businessInfoDTO.getBusinessNumber();
        this.businessName = businessInfoDTO.getBusinessName();
        this.businessType = businessInfoDTO.getBusinessType();
        this.address = Address.of(businessInfoDTO.getAddress());
        this.fax = businessInfoDTO.getFax();
        this.emailAddress = businessInfoDTO.getEmailAddress();
        this.representative = businessInfoDTO.getRepresentative();
        this.representativePhoneNumber = businessInfoDTO.getRepresentativePhoneNumber();
        this.manager = businessInfoDTO.getManager();
        this.managerPhoneNumber = businessInfoDTO.getManagerPhoneNumber();
    }

    public static BusinessInfo of(BusinessInfoDTO businessInfoDTO) {
        if (businessInfoDTO == null) {
            return new BusinessInfo();
        }
        return new BusinessInfo(businessInfoDTO);
    }
}
