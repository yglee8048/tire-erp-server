package com.minsoo.co.tireerpserver.model.dto.management.vendor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class VendorUpdateRequest {

    @NotNull
    @JsonProperty("vendor_id")
    private Long vendorId;

    @NotNull
    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;
}
