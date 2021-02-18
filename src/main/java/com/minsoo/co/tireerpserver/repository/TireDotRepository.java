package com.minsoo.co.tireerpserver.repository;

import com.minsoo.co.tireerpserver.model.entity.TireDot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TireDotRepository extends JpaRepository<TireDot, Long> {

    List<TireDot> findAllByTire_Id(Long tireId);

    Optional<TireDot> findByTire_IdAndDot(Long tireId, String dot);
}
