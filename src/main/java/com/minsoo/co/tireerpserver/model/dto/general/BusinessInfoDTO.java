package com.minsoo.co.tireerpserver.model.dto.general;

import com.minsoo.co.tireerpserver.model.entity.embedded.BusinessInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessInfoDTO {

    @ApiModelProperty(value = "사업자 번호", example = "107-20-59931")
    private String businessNumber;

    @ApiModelProperty(value = "상호", example = "피렐리")
    private String businessName;

    @ApiModelProperty(value = "업종", example = "자동차 및 부품 판매업")
    private String businessType;

    @ApiModelProperty(value = "주소")
    private AddressDTO address;

    @ApiModelProperty(value = "팩스", example = "02-709-5089")
    private String fax;

    @ApiModelProperty(value = "이메일 주소", example = "sample@pirelli.com")
    private String emailAddress;

    @ApiModelProperty(value = "대표자", example = "홍길동")
    private String representative;

    @ApiModelProperty(value = "대표자 전화번호", example = "010-1234-5678")
    private String representativePhoneNumber;

    @ApiModelProperty(value = "담당자", example = "김철수")
    private String manager;

    @ApiModelProperty(value = "담당자 전화번호", example = "010-1234-5678")
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
