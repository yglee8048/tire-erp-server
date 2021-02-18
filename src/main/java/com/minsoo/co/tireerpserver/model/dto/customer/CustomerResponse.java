package com.minsoo.co.tireerpserver.model.dto.customer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minsoo.co.tireerpserver.model.dto.general.BusinessInfoDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static lombok.AccessLevel.PROTECTED;

@Getter
@ToString
@NoArgsConstructor(access = PROTECTED)
public class CustomerResponse {

    @ApiModelProperty(value = "ID", example = "2937")
    @JsonProperty("customer_id")
    private Long customerId;

    @ApiModelProperty(value = "아이디", example = "user1234")
    @JsonProperty("user_id")
    private String userId;

    @ApiModelProperty(value = "비밀번호", example = "password1234*")
    @JsonProperty("user_pw")
    private String userPw;

    @ApiModelProperty(value = "이름", example = "피렐리 코리아")
    @JsonProperty("name")
    private String name;

    @ApiModelProperty(value = "설명", example = "20년 5월부터 계약 시작")
    @JsonProperty("description")
    private String description;

    @ApiModelProperty(value = "사업자 관련 정보")
    @JsonProperty("business_info")
    private BusinessInfoDTO businessInfo;
}
