package com.minsoo.co.tireerpserver.model.dto.general;

import com.minsoo.co.tireerpserver.model.entity.embedded.BusinessInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessInfoDTO {

    @Schema(name = "사업자 번호", example = "107-20-59931")
    private String businessNumber;

    @Schema(name = "상호", example = "피렐리")
    private String businessName;

    @Schema(name = "업종", example = "자동차 및 부품 판매업")
    private String businessType;

    @Schema(name = "주소")
    private AddressDTO address;

    @Schema(name = "팩스", example = "02-709-5089")
    private String fax;

    @Schema(name = "이메일 주소", example = "sample@pirelli.com")
    private String emailAddress;

    @Schema(name = "대표자", example = "홍길동")
    private String representative;

    @Schema(name = "대표자 전화번호", example = "010-1234-5678")
    private String representativePhoneNumber;

    @Schema(name = "담당자", example = "김철수")
    private String manager;

    @Schema(name = "담당자 전화번호", example = "010-1234-5678")
    private String managerPhoneNumber;

    private BusinessInfoDTO(BusinessInfo businessInfo) {
        this.businessNumber = businessInfo.getBusinessNumber();
        this.businessName = businessInfo.getBusinessName();
        this.businessType = businessInfo.getBusinessType();
        this.address = AddressDTO.of(businessInfo.getAddress());
        this.fax = businessInfo.getFax();
        this.emailAddress = businessInfo.getEmailAddress();
        this.representative = businessInfo.getRepresentative();
        this.representativePhoneNumber = businessInfo.getRepresentativePhoneNumber();
        this.manager = businessInfo.getManager();
        this.managerPhoneNumber = businessInfo.getManagerPhoneNumber();
    }

    public static BusinessInfoDTO of(BusinessInfo businessInfo) {
        return new BusinessInfoDTO(businessInfo);
    }
}
