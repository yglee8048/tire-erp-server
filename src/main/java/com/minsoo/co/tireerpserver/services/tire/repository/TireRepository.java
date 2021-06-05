package com.minsoo.co.tireerpserver.services.tire.repository;

import com.minsoo.co.tireerpserver.services.management.entity.Pattern;
import com.minsoo.co.tireerpserver.services.tire.entity.Tire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TireRepository extends JpaRepository<Tire, Long> {

    @Query("select distinct t.size from Tire t")
    List<String> findSizes();

    @Query("select distinct t.tireIdentification from Tire t")
    List<String> findTireIdentifications();

    boolean existsByTireIdentification(String tireIdentification);

    boolean existsByPattern(Pattern pattern);
}
