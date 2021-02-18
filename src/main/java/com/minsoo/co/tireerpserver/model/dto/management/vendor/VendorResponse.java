package com.minsoo.co.tireerpserver.model.dto.management.vendor;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minsoo.co.tireerpserver.model.dto.general.BusinessInfoDTO;
import com.minsoo.co.tireerpserver.model.entity.Vendor;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static lombok.AccessLevel.PROTECTED;

@Getter
@ToString
@NoArgsConstructor(access = PROTECTED)
public class VendorResponse {

    @ApiModelProperty(value = "ID", example = "2091")
    @JsonProperty("vendor_id")
    private Long vendorId;

    @ApiModelProperty(value = "이름", example = "피렐리 코리아")
    @JsonProperty("name")
    private String name;

    @ApiModelProperty(value = "설명", example = "20년 5월부터 계약 시작")
    @JsonProperty("description")
    private String description;

    @ApiModelProperty(value = "사업자 관련 정보")
    @JsonProperty("business_info")
    private BusinessInfoDTO businessInfo;

    private VendorResponse(Vendor vendor) {
        this.vendorId = vendor.getId();
        this.name = vendor.getName();
        this.businessInfo = BusinessInfoDTO.of(vendor.getBusinessInfo());
    }

    public static VendorResponse of(Vendor vendor) {
        return new VendorResponse(vendor);
    }
}
