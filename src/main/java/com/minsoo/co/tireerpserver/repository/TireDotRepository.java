package com.minsoo.co.tireerpserver.repository;

import com.minsoo.co.tireerpserver.model.entity.TireDot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TireDotRepository extends JpaRepository<TireDot, Long> {

    List<TireDot> findAllByTire_Id(Long tireId);
}
