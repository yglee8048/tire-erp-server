package com.minsoo.co.tireerpserver.services.tire.repository;

import com.minsoo.co.tireerpserver.services.tire.entity.Tire;
import com.minsoo.co.tireerpserver.services.tire.entity.TireDot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TireDotRepository extends JpaRepository<TireDot, Long> {

    List<TireDot> findAllByTire(Tire tire);

    @Query("select d.id from TireDot d where d.tire = :tire")
    List<Long> findAllIdsByTire(@Param(value = "tire") Tire tire);
    
    boolean existsByTireAndDot(Tire tire, String dot);
}