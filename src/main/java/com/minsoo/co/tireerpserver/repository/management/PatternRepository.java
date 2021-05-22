package com.minsoo.co.tireerpserver.repository.management;

import com.minsoo.co.tireerpserver.model.entity.entities.management.Brand;
import com.minsoo.co.tireerpserver.model.entity.entities.management.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PatternRepository extends JpaRepository<Pattern, Long> {

    List<Pattern> findAllByBrand(Brand brand);

    boolean existsByBrandAndName(Brand brand, String name);

    @Query("select distinct p.name from Pattern p")
    List<String> findAllNames();
}
