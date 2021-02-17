package com.minsoo.co.tireerpserver.model.dto.management.vendor;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minsoo.co.tireerpserver.model.entity.Vendor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static lombok.AccessLevel.PROTECTED;

@Getter
@ToString
@NoArgsConstructor(access = PROTECTED)
public class VendorResponse {

    @JsonProperty("vendor_id")
    private Long vendorId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("business_number")
    private String businessNumber;

    @JsonProperty("business_name")
    private String businessName;

    @JsonProperty("business_type")
    private String businessType;

    @JsonProperty("company_name")
    private String companyName;

    @JsonProperty("city")
    private String city;

    @JsonProperty("street_address")
    private String streetAddress;

    @JsonProperty("detail_address")
    private String detailAddress;

    @JsonProperty("zip_code")
    private Integer zipCode;

    @JsonProperty("fax")
    private String fax;

    @JsonProperty("email_address")
    private String emailAddress;

    @JsonProperty("representative")
    private String representative;

    @JsonProperty("representative_phone_number")
    private String representativePhoneNumber;

    @JsonProperty("manager")
    private String manager;

    @JsonProperty("manager_phone_number")
    private String managerPhoneNumber;

    private VendorResponse(Vendor vendor) {
        this.vendorId = vendor.getId();
        this.name = vendor.getName();
        this.businessNumber = vendor.getBusinessNumber();
        this.businessName = vendor.getBusinessName();
        this.businessType = vendor.getBusinessType();
        this.companyName = vendor.getCompanyName();
        this.city = vendor.getAddress().getCity();
        this.streetAddress = vendor.getAddress().getStreetAddress();
        this.detailAddress = vendor.getAddress().getDetailAddress();
        this.zipCode = vendor.getAddress().getZipCode();
        this.fax = vendor.getFax();
        this.emailAddress = vendor.getEmailAddress();
        this.representative = vendor.getRepresentative();
        this.representativePhoneNumber = vendor.getRepresentativePhoneNumber();
        this.manager = vendor.getManager();
        this.managerPhoneNumber = vendor.getManagerPhoneNumber();
    }

    public static VendorResponse of(Vendor vendor) {
        return new VendorResponse(vendor);
    }
}
