package com.minsoo.co.tireerpserver.services.sale.repository;

import com.minsoo.co.tireerpserver.services.sale.entity.SaleContent;
import com.minsoo.co.tireerpserver.services.sale.repository.query.SaleContentQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleContentRepository extends JpaRepository<SaleContent, Long>, SaleContentQueryRepository {
}
