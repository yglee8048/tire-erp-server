package com.minsoo.co.tireerpserver.repository.management;

import com.minsoo.co.tireerpserver.model.entity.management.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    boolean existsByName(String name);

    @Query("select distinct b.name from Brand b")
    List<String> findAllNames();
}
