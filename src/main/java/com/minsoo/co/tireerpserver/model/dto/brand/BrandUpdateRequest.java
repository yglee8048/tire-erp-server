package com.minsoo.co.tireerpserver.model.dto.brand;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class BrandUpdateRequest {

    @NotEmpty
    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;
}
