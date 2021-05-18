package com.minsoo.co.tireerpserver.repository.sale;

import com.minsoo.co.tireerpserver.model.entity.entities.sale.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long> {
}
