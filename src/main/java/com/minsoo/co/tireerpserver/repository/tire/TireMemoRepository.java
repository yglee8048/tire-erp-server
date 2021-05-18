package com.minsoo.co.tireerpserver.repository.tire;

import com.minsoo.co.tireerpserver.model.entity.entities.tire.Tire;
import com.minsoo.co.tireerpserver.model.entity.entities.tire.TireMemo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TireMemoRepository extends JpaRepository<TireMemo, Long> {

    List<TireMemo> findAllByTire(Tire tire);
}
