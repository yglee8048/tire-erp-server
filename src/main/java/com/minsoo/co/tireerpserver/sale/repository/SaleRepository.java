package com.minsoo.co.tireerpserver.sale.repository;

import com.minsoo.co.tireerpserver.sale.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long> {
}
