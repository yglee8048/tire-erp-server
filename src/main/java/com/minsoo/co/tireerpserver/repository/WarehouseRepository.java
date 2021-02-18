package com.minsoo.co.tireerpserver.repository;

import com.minsoo.co.tireerpserver.model.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

    boolean existsByName(String name);

    @Query("select warehouse.name from Warehouse warehouse")
    List<String> findAllWarehouseNames();

    Optional<Warehouse> findByName(String name);
}
