package com.minsoo.co.tireerpserver.repository;

import com.minsoo.co.tireerpserver.model.entity.Stock;
import com.minsoo.co.tireerpserver.repository.query.StockQueryRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long>, StockQueryRepository {

    @Query("select s from Stock s " +
            "join fetch s.warehouse w " +
            "join fetch s.tireDot d " +
            "where d.tire.id = :tireId")
    List<Stock> findAllByTireId(@Param("tireId") Long tireId);

    @EntityGraph(attributePaths = {"tireDot", "warehouse"})
    Optional<Stock> findFetchDotAndWarehouseById(Long stockId);

    @Query("select s from Stock s " +
            "join s.warehouse w " +
            "where s.tireDot.id = :tireDotId " +
            "and w.name = :warehouseName")
    Optional<Stock> findOneByTireDotIdAndWarehouseName(@Param("tireDotId") Long tireDotId,
                                                       @Param("warehouseName") String warehouseName);
}
