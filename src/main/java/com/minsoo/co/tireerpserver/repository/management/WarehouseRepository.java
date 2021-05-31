package com.minsoo.co.tireerpserver.repository.management;

import com.minsoo.co.tireerpserver.model.entity.management.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

    boolean existsByName(String name);
}
