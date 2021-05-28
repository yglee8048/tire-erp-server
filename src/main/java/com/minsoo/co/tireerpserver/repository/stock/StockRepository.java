package com.minsoo.co.tireerpserver.repository.stock;

import com.minsoo.co.tireerpserver.model.entity.stock.Stock;
import com.minsoo.co.tireerpserver.model.entity.tire.TireDot;
import com.minsoo.co.tireerpserver.repository.query.StockQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Long>, StockQueryRepository {

    List<Stock> findAllByTireDot(TireDot tireDot);
}
