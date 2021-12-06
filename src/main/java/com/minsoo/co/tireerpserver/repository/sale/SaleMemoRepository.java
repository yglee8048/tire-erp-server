package com.minsoo.co.tireerpserver.repository.sale;

import com.minsoo.co.tireerpserver.entity.sale.Sale;
import com.minsoo.co.tireerpserver.entity.sale.SaleMemo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleMemoRepository extends JpaRepository<SaleMemo, Long> {

    List<SaleMemo> findAllBySale(Sale sale);
}
