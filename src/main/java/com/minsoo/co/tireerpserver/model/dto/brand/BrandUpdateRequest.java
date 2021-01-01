package com.minsoo.co.tireerpserver.model.dto.brand;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BrandUpdateRequest {

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;
}
