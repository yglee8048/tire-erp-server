package com.minsoo.co.tireerpserver.model.dto.management.vendor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class VendorCreateRequest {

    @NotNull
    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;
}
