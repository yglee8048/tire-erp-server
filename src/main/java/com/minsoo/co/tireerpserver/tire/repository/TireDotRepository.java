package com.minsoo.co.tireerpserver.tire.repository;

import com.minsoo.co.tireerpserver.tire.entity.Tire;
import com.minsoo.co.tireerpserver.tire.entity.TireDot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TireDotRepository extends JpaRepository<TireDot, Long> {

    List<TireDot> findAllByTire(Tire tire);

    @Query("select d.id from TireDot d where d.tire = :tire")
    List<Long> findAllIdsByTire(@Param(value = "tire") Tire tire);

    Optional<TireDot> findByTireAndDot(Tire tire, String dot);

    boolean existsByTireAndDot(Tire tire, String dot);
}
