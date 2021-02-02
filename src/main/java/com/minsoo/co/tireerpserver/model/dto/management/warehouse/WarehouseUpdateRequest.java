package com.minsoo.co.tireerpserver.model.dto.management.warehouse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@Data
public class WarehouseUpdateRequest {

    @NotEmpty
    @JsonProperty("warehouse_id")
    private Long warehouseId;

    @NotEmpty
    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @Positive
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
    @Positive
    @JsonProperty("zip_code")
    private Integer zipCode;
}
