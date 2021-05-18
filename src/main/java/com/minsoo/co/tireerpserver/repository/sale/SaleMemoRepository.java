package com.minsoo.co.tireerpserver.repository.sale;

import com.minsoo.co.tireerpserver.model.entity.entities.sale.SaleMemo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleMemoRepository extends JpaRepository<SaleMemo, Long> {
}
