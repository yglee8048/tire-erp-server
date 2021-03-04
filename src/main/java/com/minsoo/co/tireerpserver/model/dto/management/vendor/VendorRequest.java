package com.minsoo.co.tireerpserver.model.dto.management.vendor;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minsoo.co.tireerpserver.model.dto.general.BusinessInfoDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VendorRequest {

    @ApiModelProperty(value = "이름", example = "피렐리 코리아", required = true)
    @NotEmpty(message = "매입처 이름은 필수 값입니다.")
    @JsonProperty("name")
    private String name;

    @ApiModelProperty(value = "설명", example = "20년 5월부터 계약 시작")
    @JsonProperty("description")
    private String description;

    @ApiModelProperty(value = "사업자 관련 정보")
    @JsonProperty("business_info")
    private BusinessInfoDTO businessInfo;
}
