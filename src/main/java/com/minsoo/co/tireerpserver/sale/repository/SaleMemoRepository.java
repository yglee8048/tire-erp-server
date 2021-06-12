package com.minsoo.co.tireerpserver.sale.repository;

import com.minsoo.co.tireerpserver.sale.entity.Sale;
import com.minsoo.co.tireerpserver.sale.entity.SaleMemo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleMemoRepository extends JpaRepository<SaleMemo, Long> {

    List<SaleMemo> findAllBySale(Sale sale);
}
