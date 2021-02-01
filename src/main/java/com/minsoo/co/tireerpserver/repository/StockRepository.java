package com.minsoo.co.tireerpserver.repository;

import com.minsoo.co.tireerpserver.model.entity.Stock;
import com.minsoo.co.tireerpserver.repository.query.StockQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Long>, StockQueryRepository {

    @Query("select s from Stock s " +
            "join fetch s.warehouse w " +
            "join fetch s.tireDot d " +
            "where d.tire.id = :tireId")
    List<Stock> findAllByTireId(@Param("tireId") Long tireId);
}
