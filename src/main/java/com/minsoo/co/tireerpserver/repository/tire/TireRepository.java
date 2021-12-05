package com.minsoo.co.tireerpserver.repository.tire;

import com.minsoo.co.tireerpserver.entity.tire.Tire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TireRepository extends JpaRepository<Tire, Long> {
}
