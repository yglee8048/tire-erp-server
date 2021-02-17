package com.minsoo.co.tireerpserver.model.dto.management.vendor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class VendorCreateRequest {

    @NotNull
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
}
