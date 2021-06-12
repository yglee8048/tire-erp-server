package com.minsoo.co.tireerpserver.tire.repository;

import com.minsoo.co.tireerpserver.tire.entity.Tire;
import com.minsoo.co.tireerpserver.tire.entity.TireMemo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TireMemoRepository extends JpaRepository<TireMemo, Long> {

    List<TireMemo> findAllByTire(Tire tire);
}
