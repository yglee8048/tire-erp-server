package com.minsoo.co.tireerpserver.repository.sale;

import com.minsoo.co.tireerpserver.entity.sale.Sale;
import com.minsoo.co.tireerpserver.entity.sale.SaleContent;
import com.minsoo.co.tireerpserver.entity.stock.Stock;
import com.minsoo.co.tireerpserver.entity.tire.TireDot;
import com.minsoo.co.tireerpserver.repository.sale.query.SaleContentQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SaleContentRepository extends JpaRepository<SaleContent, Long>, SaleContentQueryRepository {

    List<SaleContent> findAllBySale(Sale sale);

    Optional<SaleContent> findBySaleAndTireDotAndStock(Sale sale, TireDot tireDot, Stock stock);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("delete from SaleContent c where c.id in :ids")
    void deleteAllByIdIn(List<Long> ids);
}
