package com.minsoo.co.tireerpserver.repository.stock;

import com.minsoo.co.tireerpserver.entity.management.Warehouse;
import com.minsoo.co.tireerpserver.entity.stock.Stock;
import com.minsoo.co.tireerpserver.entity.tire.TireDot;
import com.minsoo.co.tireerpserver.repository.stock.query.StockQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long>, StockQueryRepository {

    List<Stock> findAllByTireDot(TireDot tireDot);

    Optional<Stock> findByTireDotAndWarehouseAndNickname(TireDot tireDot, Warehouse warehouse, String nickname);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("delete from Stock s where s.id in :ids")
    void deleteAllByIdIn(List<Long> ids);
}
