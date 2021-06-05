package com.minsoo.co.tireerpserver.services.sale.repository;

import com.minsoo.co.tireerpserver.services.sale.entity.SaleMemo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleMemoRepository extends JpaRepository<SaleMemo, Long> {
}
