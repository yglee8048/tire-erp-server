package com.minsoo.co.tireerpserver.entity.management;

import com.minsoo.co.tireerpserver.entity.Address;
import com.minsoo.co.tireerpserver.entity.BaseEntity;
import com.minsoo.co.tireerpserver.model.request.management.WarehouseRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "warehouse")
public class Warehouse extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "warehouse_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Embedded
    private Address address;

    public static Warehouse of(WarehouseRequest request) {
        Warehouse warehouse = new Warehouse();
        return warehouse.update(request);
    }

    public Warehouse update(WarehouseRequest request) {
        this.name = request.getName();
        this.description = request.getDescription();
        this.address = new Address(request.getAddress());
        return this;
    }
}
