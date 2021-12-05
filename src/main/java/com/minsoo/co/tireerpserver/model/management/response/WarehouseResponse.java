package com.minsoo.co.tireerpserver.model.management.response;

import com.minsoo.co.tireerpserver.entity.management.Warehouse;
import com.minsoo.co.tireerpserver.model.AddressDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WarehouseResponse {

    private Long id;
    private String name;
    private String description;
    private AddressDTO address;

    public WarehouseResponse(Warehouse warehouse) {
        this.id = warehouse.getId();
        this.name = warehouse.getName();
        this.description = warehouse.getDescription();
        this.address = new AddressDTO(warehouse.getAddress());
    }
}
