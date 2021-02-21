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

    /**
     * StockResponse 조회
     */
    @Query("select s from Stock s " +
            "   join fetch s.warehouse w " +
            "   join fetch s.tireDot d " +
            "   join fetch d.tire t " +
            "   join fetch t.brand b")
    List<Stock> findAllFetchAll();

    @Query("select s from Stock s " +
            "   join fetch s.warehouse w " +
            "   join fetch s.tireDot d " +
            "   join fetch d.tire t " +
            "   join fetch t.brand b " +
            "where s.id = :id")
    Optional<Stock> findOneFetchAllById(@Param("id") Long id);

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

    boolean existsByTireDotIn(@Param("tireDot") List<TireDot> tireDots);

    @Query("select sum(s.quantity) from Stock s where s.tireDot in :tireDots")
    Long sumQuantityByTireDots(@Param("tireDots") List<TireDot> tireDots);
}
