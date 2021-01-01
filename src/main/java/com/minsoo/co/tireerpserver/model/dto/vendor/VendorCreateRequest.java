package com.minsoo.co.tireerpserver.model.dto.vendor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class VendorCreateRequest {

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;
}
