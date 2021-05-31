package com.minsoo.co.tireerpserver.repository.tire;

import com.minsoo.co.tireerpserver.model.entity.tire.Tire;
import com.minsoo.co.tireerpserver.model.entity.tire.TireDot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TireDotRepository extends JpaRepository<TireDot, Long> {

    List<TireDot> findAllByTire(Tire tire);
    
    boolean existsByTireAndDot(Tire tire, String dot);
}
