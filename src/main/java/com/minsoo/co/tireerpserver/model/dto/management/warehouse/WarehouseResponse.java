package com.minsoo.co.tireerpserver.model.dto.management.warehouse;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minsoo.co.tireerpserver.model.dto.general.AddressDTO;
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

    @ApiModelProperty(value = "용량", example = "20000")
    @JsonProperty("capacity")
    private Integer capacity;

    @ApiModelProperty(value = "address")
    private AddressDTO address;

    private WarehouseResponse(Warehouse warehouse) {
        this.warehouseId = warehouse.getId();
        this.name = warehouse.getName();
        this.description = warehouse.getDescription();
        this.capacity = warehouse.getCapacity();
        this.address = AddressDTO.of(warehouse.getAddress());
    }

    public static WarehouseResponse of(Warehouse warehouse) {
        return new WarehouseResponse(warehouse);
    }
}
