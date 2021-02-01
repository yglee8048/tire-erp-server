package com.minsoo.co.tireerpserver.model.dto.management.warehouse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class WarehouseCreateRequest {

    @NotEmpty
    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("capacity")
    private Integer capacity;

    @JsonProperty("address_city")
    private String addressCity;

    // 도로명 주소
    @JsonProperty("address_street")
    private String addressStreet;

    // 상세 주소
    @JsonProperty("address_detail")
    private String addressDetail;

    // 우편번호
    @JsonProperty("zip_code")
    private Integer zipCode;
}
