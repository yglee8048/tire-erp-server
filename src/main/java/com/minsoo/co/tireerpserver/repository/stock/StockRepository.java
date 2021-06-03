package com.minsoo.co.tireerpserver.repository.stock;

import com.minsoo.co.tireerpserver.model.entity.stock.Stock;
import com.minsoo.co.tireerpserver.model.entity.tire.TireDot;
import com.minsoo.co.tireerpserver.repository.query.StockQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Long>, StockQueryRepository {

    List<Stock> findAllByTireDot(TireDot tireDot);

    @Query("select s from Stock s where s.tireDot.id in :tireDotIds")
    List<Stock> findAllByTireDot_IdIn(@Param(value = "tireDotIds") List<Long> tireDotIds);
}
