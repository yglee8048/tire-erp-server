package com.minsoo.co.tireerpserver.repository.tire;

import com.minsoo.co.tireerpserver.entity.tire.Tire;
import com.minsoo.co.tireerpserver.entity.tire.TireDot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TireDotRepository extends JpaRepository<TireDot, Long>, TireDotQueryRepository {

    List<TireDot> findAllByTire(Tire tire);

    Optional<TireDot> findByTireAndDot(Tire tire, String dot);
}
