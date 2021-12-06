package com.minsoo.co.tireerpserver.repository.sale;

import com.minsoo.co.tireerpserver.entity.sale.Sale;
import com.minsoo.co.tireerpserver.entity.sale.SaleContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleContentRepository extends JpaRepository<SaleContent, Long> {

    List<SaleContent> findAllBySale(Sale sale);
}
