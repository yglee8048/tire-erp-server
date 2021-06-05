package com.minsoo.co.tireerpserver.services.management.repository;

import com.minsoo.co.tireerpserver.services.management.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

    boolean existsByName(String name);
}
