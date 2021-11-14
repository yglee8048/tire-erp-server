package com.minsoo.co.tireerpserver.stock.repository;

import com.minsoo.co.tireerpserver.stock.entity.Stock;
import com.minsoo.co.tireerpserver.stock.repository.query.StockQueryRepository;
import com.minsoo.co.tireerpserver.tire.entity.TireDot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long>, StockQueryRepository {

    List<Stock> findAllByTireDot(TireDot tireDot);

    @Query("select s from Stock s where s.tireDot.id in :tireDotIds")
    List<Stock> findAllByTireDot_IdIn(@Param(value = "tireDotIds") List<Long> tireDotIds);

    Optional<Stock> findByTireDotAndNickname(TireDot tireDot, String nickname);
}
