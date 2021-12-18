package com.minsoo.co.tireerpserver.model;

import com.minsoo.co.tireerpserver.entity.BusinessInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BusinessInfoDTO {

    @Schema(name = "business_number", description = "사업자 등록번호")
    private String businessNumber;
    @Schema(name = "business_name", description = "이름")
    private String businessName;
    @Schema(name = "business_type", description = "유형")
    private String businessType;
    @Schema(name = "address", description = "주소")
    private AddressDTO address;
    @Schema(name = "fax", description = "팩스번호")
    private String fax;
    @Schema(name = "email", description = "이메일 주소")
    private String email;
    @Schema(name = "representative", description = "대표")
    private String representative;
    @Schema(name = "representativePhoneNumber", description = "대표 번호")
    private String representativePhoneNumber;
    @Schema(name = "manager", description = "담당자")
    private String manager;
    @Schema(name = "managerPhoneNumber", description = "담당자 번호")
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