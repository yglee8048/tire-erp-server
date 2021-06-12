package com.minsoo.co.tireerpserver.sale.repository;

import com.minsoo.co.tireerpserver.sale.entity.Sale;
import com.minsoo.co.tireerpserver.sale.repository.query.SaleContentQueryRepository;
import com.minsoo.co.tireerpserver.sale.entity.SaleContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleContentRepository extends JpaRepository<SaleContent, Long>, SaleContentQueryRepository {

    List<SaleContent> findAllBySale(Sale sale);
}
