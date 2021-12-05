package com.minsoo.co.tireerpserver.model;

import com.minsoo.co.tireerpserver.entity.BusinessInfo;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BusinessInfoDTO {

    private String businessNumber;
    private String businessName;
    private String businessType;
    private AddressDTO address;
    private String fax;
    private String email;
    private String representative;
    private String representativePhoneNumber;
    private String manager;
    private String managerPhoneNumber;

    public BusinessInfoDTO(BusinessInfo businessInfo) {
        if (businessInfo == null) {
            return;
        }

        this.businessNumber = businessInfo.getBusinessNumber();
        this.businessName = businessInfo.getBusinessName();
        this.businessType = businessInfo.getBusinessType();
        this.address = new AddressDTO(businessInfo.getAddress());
        this.fax = businessInfo.getFax();
        this.email = businessInfo.getEmail();
        this.representative = businessInfo.getRepresentative();
        this.representativePhoneNumber = businessInfo.getRepresentativePhoneNumber();
        this.manager = businessInfo.getManager();
        this.managerPhoneNumber = businessInfo.getManagerPhoneNumber();
    }
}