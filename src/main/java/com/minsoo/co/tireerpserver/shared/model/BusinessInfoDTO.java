package com.minsoo.co.tireerpserver.shared.model;

import com.minsoo.co.tireerpserver.shared.entity.BusinessInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
public class BusinessInfoDTO {

    @Schema(name = "business_number", description = "사업자 번호", example = "107-20-59931")
    private String businessNumber;

    @Schema(name = "business_name", description = "상호", example = "피렐리")
    private String businessName;

    @Schema(name = "business_type", description = "업종", example = "자동차 및 부품 판매업")
    private String businessType;

    @Schema(name = "address", description = "주소")
    private AddressDTO address;

    @Schema(name = "fax", description = "팩스", example = "02-709-5089")
    private String fax;

    @Schema(name = "email", description = "이메일 주소", example = "sample@pirelli.com")
    private String email;

    @Schema(name = "representative", description = "대표자", example = "홍길동")
    private String representative;

    @Schema(name = "representative_phone_number", description = "대표자 전화번호", example = "010-1234-5678")
    private String representativePhoneNumber;

    @Schema(name = "manager", description = "담당자", example = "김철수")
    private String manager;

    @Schema(name = "manager_phone_number", description = "담당자 전화번호", example = "010-1234-5678")
    private String managerPhoneNumber;

    public BusinessInfoDTO() {
        this.address = new AddressDTO();
    }

    public BusinessInfoDTO(BusinessInfo businessInfo) {
        this.businessNumber = businessInfo.getBusinessNumber();
        this.businessName = businessInfo.getBusinessName();
        this.businessType = businessInfo.getBusinessType();
        this.address = AddressDTO.of(businessInfo.getAddress());
        this.fax = businessInfo.getFax();
        this.email = businessInfo.getEmail();
        this.representative = businessInfo.getRepresentative();
        this.representativePhoneNumber = businessInfo.getRepresentativePhoneNumber();
        this.manager = businessInfo.getManager();
        this.managerPhoneNumber = businessInfo.getManagerPhoneNumber();
    }

    public static BusinessInfoDTO of(BusinessInfo businessInfo) {
        if (businessInfo == null) {
            return new BusinessInfoDTO();
        }
        return new BusinessInfoDTO(businessInfo);
    }
}
