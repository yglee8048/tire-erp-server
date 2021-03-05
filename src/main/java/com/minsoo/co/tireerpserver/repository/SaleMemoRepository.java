package com.minsoo.co.tireerpserver.repository;

import com.minsoo.co.tireerpserver.model.entity.SaleMemo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleMemoRepository extends JpaRepository<SaleMemo, Long> {
}
