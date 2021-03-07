package com.minsoo.co.tireerpserver.repository;

import com.minsoo.co.tireerpserver.model.entity.Sale;
import com.minsoo.co.tireerpserver.model.entity.SaleContent;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SaleContentRepository extends JpaRepository<SaleContent, Long> {

    @EntityGraph(attributePaths = {"sale", "tireDot"})
    @Query("select content from SaleContent content order by content.sale.id")
    List<SaleContent> findAllOrderBySaleId();

    @EntityGraph(attributePaths = {"sale", "tireDot"})
    List<SaleContent> findAllBySaleOrderBySale(Sale sale);
}
