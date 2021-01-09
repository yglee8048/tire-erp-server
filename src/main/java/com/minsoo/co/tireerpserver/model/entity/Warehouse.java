package com.minsoo.co.tireerpserver.model.entity;

import com.minsoo.co.tireerpserver.model.dto.warehouse.WarehouseCreateRequest;
import com.minsoo.co.tireerpserver.model.dto.warehouse.WarehouseUpdateRequest;
import com.minsoo.co.tireerpserver.model.entity.audit.BaseEntity;
import com.minsoo.co.tireerpserver.model.entity.embedded.Address;
import com.minsoo.co.tireerpserver.model.entity.embedded.AdminHistory;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "warehouse", uniqueConstraints = {@UniqueConstraint(name = "warehouse_name_unique", columnNames = {"name"})})
public class Warehouse extends BaseEntity {

    @Id
    @Column(name = "warehouse_id", length = 20)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "capacity")
    private Integer capacity;

    @Embedded
    private Address address;

    @Embedded
    private AdminHistory history;

    private Warehouse(WarehouseCreateRequest createRequest, Admin operator) {
        this.name = createRequest.getName();
        this.description = createRequest.getDescription();
        this.capacity = createRequest.getCapacity();
        this.address = new Address(createRequest.getAddressCity(), createRequest.getAddressStreet(), createRequest.getAddressDetail(), createRequest.getZipCode());
        this.history = new AdminHistory(operator);
    }

    public static Warehouse create(WarehouseCreateRequest createRequest, Admin operator) {
        return new Warehouse(createRequest, operator);
    }

    public void update(WarehouseUpdateRequest updateRequest, Admin operator) {
        this.name = updateRequest.getName();
        this.description = updateRequest.getDescription();
        this.capacity = updateRequest.getCapacity();
        this.address = new Address(updateRequest.getAddressCity(), updateRequest.getAddressStreet(), updateRequest.getAddressDetail(), updateRequest.getZipCode());
        this.history = new AdminHistory(operator);
    }
}
