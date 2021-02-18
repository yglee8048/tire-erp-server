package com.minsoo.co.tireerpserver.model.dto.management.warehouse;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@ToString
@Builder
@AllArgsConstructor
public class WarehouseSimpleResponse {

    @ApiModelProperty(value = "창고 ID", example = "2099")
    @JsonProperty("warehouse_id")
    private Long warehouseId;

    @ApiModelProperty(value = "창고 이름", example = "왕십리 메인 창고")
    @JsonProperty("name")
    private String name;
}
