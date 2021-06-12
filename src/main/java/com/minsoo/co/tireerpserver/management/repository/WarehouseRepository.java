package com.minsoo.co.tireerpserver.management.repository;

import com.minsoo.co.tireerpserver.management.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

    boolean existsByName(String name);
}
