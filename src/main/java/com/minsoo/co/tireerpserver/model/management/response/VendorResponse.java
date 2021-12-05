package com.minsoo.co.tireerpserver.model.management.response;

import com.minsoo.co.tireerpserver.entity.management.Vendor;
import com.minsoo.co.tireerpserver.model.BusinessInfoDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VendorResponse {

    private Long vendorId;
    private String name;
    private String description;
    private BusinessInfoDTO businessInfo;

    public VendorResponse(Vendor vendor) {
        this.vendorId = vendor.getId();
        this.name = vendor.getName();
        this.description = vendor.getDescription();
        this.businessInfo = new BusinessInfoDTO(vendor.getBusinessInfo());
    }
}
