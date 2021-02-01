package com.minsoo.co.tireerpserver.model.dto.management.brand;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class BrandUpdateRequest {

    @NotEmpty
    @JsonProperty("brand_id")
    private Long brandId;

    @NotEmpty
    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;
}
