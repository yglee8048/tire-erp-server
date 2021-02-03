package com.minsoo.co.tireerpserver.model.dto.management.vendor;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minsoo.co.tireerpserver.model.entity.Vendor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static lombok.AccessLevel.PROTECTED;

@Getter
@ToString
@NoArgsConstructor(access = PROTECTED)
public class VendorResponse {

    @JsonProperty("vendor_id")
    private Long vendorId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    private VendorResponse(Vendor vendor) {
        this.vendorId = vendor.getId();
        this.name = vendor.getName();
        this.description = vendor.getDescription();
    }

    public static VendorResponse of(Vendor vendor) {
        return new VendorResponse(vendor);
    }
}
