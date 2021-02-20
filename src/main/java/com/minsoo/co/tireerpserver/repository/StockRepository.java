package com.minsoo.co.tireerpserver.repository;

import com.minsoo.co.tireerpserver.model.entity.Stock;
import com.minsoo.co.tireerpserver.model.entity.TireDot;
import com.minsoo.co.tireerpserver.model.entity.Warehouse;
import com.minsoo.co.tireerpserver.repository.query.StockQueryRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long>, StockQueryRepository {

    @Query("select s from Stock s " +
            "   join fetch s.warehouse w " +
            "   join fetch s.tireDot d " +
            "where d.tire.id = :tireId")
    List<Stock> findAllFetchByTireId(@Param("tireId") Long tireId);

    @EntityGraph(attributePaths = {"tireDot", "warehouse"})
    Optional<Stock> findFetchDotAndWarehouseById(Long stockId);

    @EntityGraph(attributePaths = {"tireDot", "warehouse"})
    Optional<Stock> findFetchById(Long stockId);

    @Query("select s from Stock s " +
            "   join fetch s.warehouse w " +
            "   join fetch s.tireDot d " +
            "where d.id = :tireDotId " +
            "   and w.id = :warehouseId")
    Optional<Stock> findOneFetchByTireDotIdAndWarehouseId(@Param("tireDotId") Long tireDotId,
                                                          @Param("warehouseId") Long warehouseId);

    boolean existsByTireDotIn(@Param("tireDot") List<TireDot> tireDots);

    @Query("select sum(s.quantity) from Stock s where s.tireDot in :tireDots")
    Long sumQuantityByTireDots(@Param("tireDots") List<TireDot> tireDots);
}
