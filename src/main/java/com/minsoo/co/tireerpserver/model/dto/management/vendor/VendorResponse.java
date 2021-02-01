package com.minsoo.co.tireerpserver.model.dto.management.vendor;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minsoo.co.tireerpserver.model.entity.Vendor;
import lombok.Data;

@Data
public class VendorResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    public VendorResponse(Vendor vendor) {
        this.id = vendor.getId();
        this.name = vendor.getName();
        this.description = vendor.getDescription();
    }
}
