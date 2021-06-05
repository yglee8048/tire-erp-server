package com.minsoo.co.tireerpserver.services.sale.repository;

import com.minsoo.co.tireerpserver.services.sale.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long> {
}
