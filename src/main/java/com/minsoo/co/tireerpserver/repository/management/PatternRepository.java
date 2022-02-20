package com.minsoo.co.tireerpserver.repository.management;

import com.minsoo.co.tireerpserver.entity.management.Brand;
import com.minsoo.co.tireerpserver.entity.management.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatternRepository extends JpaRepository<Pattern, Long> {

    List<Pattern> findAllByBrand(Brand brand);
}
