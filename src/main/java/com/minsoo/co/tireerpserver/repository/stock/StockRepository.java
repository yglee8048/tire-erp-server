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

    /**
     * StockSimpleResponse 조회
     */
    @Query("select s from Stock s " +
            "   join fetch s.warehouse w " +
            "   join fetch s.tireDot d " +
            "where d.tire.id = :tireId")
    List<Stock> findAllFetchWarehouseAndTireDotByTireId(@Param("tireId") Long tireId);

    @EntityGraph(attributePaths = {"tireDot", "warehouse"})
    Optional<Stock> findOneFetchWarehouseAndTireDotById(Long stockId);
    
    @Query("select s from Stock s " +
            "where s.tireDot = :tireDot " +
            "   and s.warehouse = :warehouse")
    Optional<Stock> findOneByTireDotAndWarehouse(@Param("tireDot") TireDot tireDot,
                                                 @Param("warehouse") Warehouse warehouse);

    @EntityGraph(attributePaths = {"warehouse", "tireDot"})
    @Query("select s from Stock s " +
            "where s.tireDot = :tireDot " +
            "   and s.warehouse = :warehouse")
    Optional<Stock> findOneFetchWarehouseAndTireDotByWarehouseAndTireDot(@Param("warehouse") Warehouse warehouse,
                                                                         @Param("tireDot") TireDot tireDot);
}
