package com.minsoo.co.tireerpserver.repository.sale;

import com.minsoo.co.tireerpserver.entity.sale.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long> {
}
