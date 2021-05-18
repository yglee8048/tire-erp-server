package com.minsoo.co.tireerpserver.model.entity.entities.management;

import com.minsoo.co.tireerpserver.model.dto.management.warehouse.WarehouseRequest;
import com.minsoo.co.tireerpserver.model.entity.embedded.Address;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "warehouse", uniqueConstraints = {@UniqueConstraint(name = "warehouse_unique_name", columnNames = {"name"})})
public class Warehouse {

    @Id
    @Column(name = "warehouse_id", length = 20)
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "capacity")
    private Integer capacity;

    @Embedded
    private Address address;

    //== Business ==//
    private Warehouse(WarehouseRequest createRequest) {
        this.name = createRequest.getName();
        this.description = createRequest.getDescription();
        this.capacity = createRequest.getCapacity();
        this.address = Address.of(createRequest.getAddress());
    }

    public static Warehouse of(WarehouseRequest createRequest) {
        return new Warehouse(createRequest);
    }

    public void update(WarehouseRequest updateRequest) {
        this.name = updateRequest.getName();
        this.description = updateRequest.getDescription();
        this.capacity = updateRequest.getCapacity();
        this.address = Address.of(updateRequest.getAddress());
    }
}
