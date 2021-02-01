package com.minsoo.co.tireerpserver.model.dto.management.warehouse;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minsoo.co.tireerpserver.model.entity.Warehouse;
import lombok.Data;

@Data
public class WarehouseResponse {

    @JsonProperty("warehouse_id")
    private Long warehouseId;

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

    public WarehouseResponse(Warehouse warehouse) {
        this.warehouseId = warehouse.getId();
        this.name = warehouse.getName();
        this.description = warehouse.getDescription();
        this.capacity = warehouse.getCapacity();
        this.addressCity = warehouse.getAddress().getCity();
        this.addressStreet = warehouse.getAddress().getStreetAddress();
        this.addressDetail = warehouse.getAddress().getDetailAddress();
        this.zipCode = warehouse.getAddress().getZipCode();
    }
}
