package com.minsoo.co.tireerpserver.repository;

import com.minsoo.co.tireerpserver.model.entity.Tire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TireRepository extends JpaRepository<Tire, Long> {

    @Query("select distinct concat(t.width, t.flatnessRatio, t.inch) from Tire t")
    List<String> findAllSizes();

    @Query("select t.productId from Tire t")
    List<String> findAllProductIds();

    @Query("select distinct t.pattern from Tire t")
    List<String> findAllPatterns();

    boolean existsByProductId(String productId);
}
