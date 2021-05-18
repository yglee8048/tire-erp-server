package com.minsoo.co.tireerpserver.model.dto.management.warehouse;

import com.minsoo.co.tireerpserver.model.entity.entities.management.Warehouse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class WarehouseSimpleResponse {

    @Schema(name = "창고 ID", example = "2099")
    private Long warehouseId;

    @Schema(name = "창고 이름", example = "왕십리 메인 창고")
    private String name;

    private WarehouseSimpleResponse(Warehouse warehouse) {
        this.warehouseId = warehouse.getId();
        this.name = warehouse.getName();
    }

    public static WarehouseSimpleResponse of(Warehouse warehouse) {
        return new WarehouseSimpleResponse(warehouse);
    }
}
