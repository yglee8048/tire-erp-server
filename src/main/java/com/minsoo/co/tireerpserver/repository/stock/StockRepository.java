package com.minsoo.co.tireerpserver.repository.stock;

import com.minsoo.co.tireerpserver.model.entity.entities.stock.Stock;
import com.minsoo.co.tireerpserver.model.entity.entities.tire.TireDot;
import com.minsoo.co.tireerpserver.model.entity.entities.management.Warehouse;
import com.minsoo.co.tireerpserver.repository.query.StockQueryRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long>, StockQueryRepository {

    List<Stock> findAllByTireDot(TireDot tireDot);
}
