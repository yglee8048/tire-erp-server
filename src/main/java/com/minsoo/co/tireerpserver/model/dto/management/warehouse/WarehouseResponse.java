package com.minsoo.co.tireerpserver.model.dto.management.warehouse;

import com.minsoo.co.tireerpserver.model.dto.general.AddressDTO;
import com.minsoo.co.tireerpserver.model.entity.entities.management.Warehouse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class WarehouseResponse {

    @Schema(name = "warehouse_id", description = "warehouse_id", example = "2099")
    private Long warehouseId;

    @Schema(name = "name", description = "이름", example = "왕십리 메인 창고", required = true)
    private String name;

    @Schema(name = "description", description = "설명", example = "메인 판매 타이어 보관")
    private String description;

    @Schema(name = "capacity", description = "용량", example = "20000")
    private Integer capacity;

    @Schema(name = "address", description = "주소")
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
