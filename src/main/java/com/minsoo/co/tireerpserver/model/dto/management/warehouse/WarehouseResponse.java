package com.minsoo.co.tireerpserver.model.dto.management.warehouse;

import com.minsoo.co.tireerpserver.model.dto.general.AddressDTO;
import com.minsoo.co.tireerpserver.model.entity.Warehouse;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class WarehouseResponse {

    @ApiModelProperty(value = "ID", example = "2099")
    private Long warehouseId;

    @ApiModelProperty(value = "이름", example = "왕십리 메인 창고")
    private String name;

    @ApiModelProperty(value = "설명", example = "메인 판매 타이어 보관")
    private String description;

    @ApiModelProperty(value = "용량", example = "20000")
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
