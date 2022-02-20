package com.minsoo.co.tireerpserver.model.response.management;

import com.minsoo.co.tireerpserver.entity.management.Warehouse;
import com.minsoo.co.tireerpserver.model.AddressDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class WarehouseResponse {

    private Long warehouseId;
    private String name;
    private String description;
    private AddressDTO address;

    private String createdBy;
    private LocalDateTime createdAt;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedAt;

    public WarehouseResponse(Warehouse warehouse) {
        this.warehouseId = warehouse.getId();
        this.name = warehouse.getName();
        this.description = warehouse.getDescription();
        this.address = new AddressDTO(warehouse.getAddress());

        this.createdAt = warehouse.getCreatedAt();
        this.lastModifiedAt = warehouse.getLastModifiedAt();
        this.createdBy = warehouse.getCreatedBy();
        this.lastModifiedBy = warehouse.getLastModifiedBy();
    }
}
