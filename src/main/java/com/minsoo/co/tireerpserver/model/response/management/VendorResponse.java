package com.minsoo.co.tireerpserver.model.response.management;

import com.minsoo.co.tireerpserver.entity.management.Vendor;
import com.minsoo.co.tireerpserver.model.BusinessInfoDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class VendorResponse {

    private Long vendorId;
    private String name;
    private String description;
    private BusinessInfoDTO businessInfo;

    private String createdBy;
    private LocalDateTime createdAt;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedAt;

    public VendorResponse(Vendor vendor) {
        this.vendorId = vendor.getId();
        this.name = vendor.getName();
        this.description = vendor.getDescription();
        this.businessInfo = new BusinessInfoDTO(vendor.getBusinessInfo());

        this.createdAt = vendor.getCreatedAt();
        this.lastModifiedAt = vendor.getLastModifiedAt();
        this.createdBy = vendor.getCreatedBy();
        this.lastModifiedBy = vendor.getLastModifiedBy();
    }
}
