package com.minsoo.co.tireerpserver.repository.tire;

import com.minsoo.co.tireerpserver.model.entity.entities.tire.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PatternRepository extends JpaRepository<Pattern, Long> {

    boolean existsByName(String name);

    @Query("select distinct p.name from Pattern p")
    List<String> findNames();
}
