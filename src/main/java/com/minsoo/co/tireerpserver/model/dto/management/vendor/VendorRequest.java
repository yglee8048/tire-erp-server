package com.minsoo.co.tireerpserver.model.dto.management.vendor;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class VendorRequest {

    @ApiModelProperty(value = "이름", example = "피렐리 코리아")
    @NotNull(message = "매입처 이름은 필수 값입니다.")
    @JsonProperty("name")
    private String name;

    @ApiModelProperty(value = "사업자 번호", example = "107-20-59931")
    @JsonProperty("business_number")
    private String businessNumber;

    @ApiModelProperty(value = "상호", example = "피렐리")
    @JsonProperty("business_name")
    private String businessName;

    @ApiModelProperty(value = "업종", example = "자동차 및 부품 판매업")
    @JsonProperty("business_type")
    private String businessType;

    @ApiModelProperty(value = "시/도", example = "서울특별시")
    @JsonProperty("city")
    private String city;

    @ApiModelProperty(value = "도로명 주소", example = "서울특별시 종로구 세종대로 209")
    @JsonProperty("street_address")
    private String streetAddress;

    @ApiModelProperty(value = "상세 주소", example = "1403호")
    @JsonProperty("detail_address")
    private String detailAddress;

    @ApiModelProperty(value = "우편번호", example = "03139")
    @Positive(message = "우편번호는 양수여야 합니다.")
    @JsonProperty("zip_code")
    private Integer zipCode;

    @ApiModelProperty(value = "팩스", example = "02-709-5089")
    @JsonProperty("fax")
    private String fax;

    @ApiModelProperty(value = "이메일 주소", example = "sample@pirelli.com")
    @JsonProperty("email_address")
    private String emailAddress;

    @ApiModelProperty(value = "대표자", example = "홍길동")
    @JsonProperty("representative")
    private String representative;

    @ApiModelProperty(value = "대표자 전화번호", example = "010-1234-5678")
    @JsonProperty("representative_phone_number")
    private String representativePhoneNumber;

    @ApiModelProperty(value = "담당자", example = "김철수")
    @JsonProperty("manager")
    private String manager;

    @ApiModelProperty(value = "담당자 전화번호", example = "010-1234-5678")
    @JsonProperty("manager_phone_number")
    private String managerPhoneNumber;
}
