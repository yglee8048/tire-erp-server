package com.minsoo.co.tireerpserver.repository.tire;

import com.minsoo.co.tireerpserver.model.entity.tire.Tire;
import com.minsoo.co.tireerpserver.model.entity.tire.TireMemo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TireMemoRepository extends JpaRepository<TireMemo, Long> {

    List<TireMemo> findAllByTire(Tire tire);
}
