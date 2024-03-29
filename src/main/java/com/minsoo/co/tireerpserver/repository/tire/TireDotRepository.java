package com.minsoo.co.tireerpserver.repository.tire;

import com.minsoo.co.tireerpserver.entity.tire.Tire;
import com.minsoo.co.tireerpserver.entity.tire.TireDot;
import com.minsoo.co.tireerpserver.repository.tire.query.TireDotQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TireDotRepository extends JpaRepository<TireDot, Long>, TireDotQueryRepository {

    Optional<TireDot> findByTireAndDot(Tire tire, String dot);
}
