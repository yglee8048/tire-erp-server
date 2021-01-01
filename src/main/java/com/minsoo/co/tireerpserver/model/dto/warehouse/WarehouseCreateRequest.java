package com.minsoo.co.tireerpserver.model.dto.warehouse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WarehouseCreateRequest {

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("capacity")
    private Integer capacity;

    @JsonProperty("city")
    private String city;

    // 도로명 주소
    @JsonProperty("street_address")
    private String street;

    // 상세 주소
    @JsonProperty("detail_address")
    private String detail;

    // 우편번호
    @JsonProperty("zip_code")
    private Integer zipCode;
}
