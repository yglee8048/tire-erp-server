package com.minsoo.co.tireerpserver.repository.tire;

import com.minsoo.co.tireerpserver.entity.tire.Tire;
import com.minsoo.co.tireerpserver.repository.tire.query.TireQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TireRepository extends JpaRepository<Tire, Long>, TireQueryRepository {
}
