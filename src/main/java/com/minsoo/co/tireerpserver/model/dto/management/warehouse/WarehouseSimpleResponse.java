package com.minsoo.co.tireerpserver.model.dto.management.warehouse;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minsoo.co.tireerpserver.model.entity.Warehouse;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class WarehouseSimpleResponse {

    @ApiModelProperty(value = "창고 ID", example = "2099")
    @JsonProperty("warehouse_id")
    private Long warehouseId;

    @ApiModelProperty(value = "창고 이름", example = "왕십리 메인 창고")
    @JsonProperty("name")
    private String name;

    private WarehouseSimpleResponse(Warehouse warehouse) {
        this.warehouseId = warehouse.getId();
        this.name = warehouse.getName();
    }

    public static WarehouseSimpleResponse of(Warehouse warehouse) {
        return new WarehouseSimpleResponse(warehouse);
    }
}
