package com.minsoo.co.tireerpserver.repository;

import com.minsoo.co.tireerpserver.model.entity.TireMemo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TireMemoRepository extends JpaRepository<TireMemo, Long> {

    List<TireMemo> findAllByTire_Id(Long tireId);
}
