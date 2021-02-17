package com.minsoo.co.tireerpserver.model.dto.management.warehouse;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minsoo.co.tireerpserver.model.entity.Warehouse;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import static lombok.AccessLevel.*;

@Getter
@ToString
@NoArgsConstructor(access = PROTECTED)
public class WarehouseResponse {

    @ApiModelProperty(value = "ID", example = "2099")
    @JsonProperty("warehouse_id")
    private Long warehouseId;

    @ApiModelProperty(value = "이름", example = "왕십리 메인 창고")
    @JsonProperty("name")
    private String name;

    @ApiModelProperty(value = "설명", example = "메인 판매 타이어 보관")
    @JsonProperty("description")
    private String description;

    @ApiModelProperty(value = "이름", example = "왕십리 메인 창고")
    @JsonProperty("capacity")
    private Integer capacity;

    @ApiModelProperty(value = "시/도", example = "서울특별시")
    @JsonProperty("city")
    private String city;

    @ApiModelProperty(value = "도로명 주소", example = "서울특별시 종로구 세종대로 209")
    @JsonProperty("street_address")
    private String streetAddress;

    @ApiModelProperty(value = "상세 주소", example = "1403호")
    @JsonProperty("detail_address")
    private String detailAddress;

    @ApiModelProperty(value = "우편번호", example = "03139")
    @JsonProperty("zip_code")
    private Integer zipCode;

    private WarehouseResponse(Warehouse warehouse) {
        this.warehouseId = warehouse.getId();
        this.name = warehouse.getName();
        this.description = warehouse.getDescription();
        this.capacity = warehouse.getCapacity();
        this.city = warehouse.getAddress().getCity();
        this.streetAddress = warehouse.getAddress().getStreetAddress();
        this.detailAddress = warehouse.getAddress().getDetailAddress();
        this.zipCode = warehouse.getAddress().getZipCode();
    }

    public static WarehouseResponse of(Warehouse warehouse) {
        return new WarehouseResponse(warehouse);
    }
}
