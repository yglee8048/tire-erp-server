package com.minsoo.co.tireerpserver.repository;

import com.minsoo.co.tireerpserver.model.entity.Tire;
import com.minsoo.co.tireerpserver.model.entity.TireDot;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TireDotRepository extends JpaRepository<TireDot, Long> {

    @Query("select d from TireDot d join fetch d.tire t join fetch t.brand b where d.id = :id")
    Optional<TireDot> findFetchById(@Param("id") Long id);

    List<TireDot> findAllByTire(Tire tire);

    Optional<TireDot> findByTireAndDot(Tire tire, String dot);
}
