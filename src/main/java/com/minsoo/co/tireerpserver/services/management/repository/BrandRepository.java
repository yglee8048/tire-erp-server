package com.minsoo.co.tireerpserver.services.management.repository;

import com.minsoo.co.tireerpserver.services.management.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    boolean existsByName(String name);

    @Query("select distinct b.name from Brand b")
    List<String> findAllNames();
}
