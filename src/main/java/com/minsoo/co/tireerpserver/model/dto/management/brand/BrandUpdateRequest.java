package com.minsoo.co.tireerpserver.model.dto.management.brand;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class BrandUpdateRequest {

    @NotNull
    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;
}
