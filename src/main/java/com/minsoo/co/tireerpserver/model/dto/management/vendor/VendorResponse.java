package com.minsoo.co.tireerpserver.model.dto.management.vendor;

import com.minsoo.co.tireerpserver.model.dto.general.BusinessInfoDTO;
import com.minsoo.co.tireerpserver.model.entity.entities.management.Vendor;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class VendorResponse {

    @Schema(name = "vendor_id", description = "vendor_id", example = "2091")
    private Long vendorId;

    @Schema(name = "name", description = "이름", example = "피렐리 코리아")
    private String name;

    @Schema(name = "description", description = "설명", example = "20년 5월부터 계약 시작")
    private String description;

    @Schema(name = "business_info", description = "사업자 관련 정보")
    private BusinessInfoDTO businessInfo;

    private VendorResponse(Vendor vendor) {
        this.vendorId = vendor.getId();
        this.name = vendor.getName();
        this.description = vendor.getDescription();
        this.businessInfo = BusinessInfoDTO.of(vendor.getBusinessInfo());
    }

    public static VendorResponse of(Vendor vendor) {
        return new VendorResponse(vendor);
    }
}
