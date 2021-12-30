package com.minsoo.co.tireerpserver.repository.stock;

import com.minsoo.co.tireerpserver.entity.management.Warehouse;
import com.minsoo.co.tireerpserver.entity.stock.Stock;
import com.minsoo.co.tireerpserver.entity.tire.TireDot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {

    List<Stock> findAllByTireDot(TireDot tireDot);

    Optional<Stock> findByTireDotAndWarehouseAndNickname(TireDot tireDot, Warehouse warehouse, String nickname);
}
