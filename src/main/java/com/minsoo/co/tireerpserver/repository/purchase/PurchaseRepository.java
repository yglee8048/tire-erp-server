package com.minsoo.co.tireerpserver.repository.purchase;

import com.minsoo.co.tireerpserver.model.entity.entities.purchase.Purchase;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    @Query("select p from Purchase p " +
            "join fetch p.warehouse w " +
            "join fetch p.vendor v " +
            "join fetch p.tireDot d " +
            "join fetch d.tire t " +
            "join fetch t.brand b " +
            "order by p.id desc")
    List<Purchase> findAllFetchAll();

    @Query("select p from Purchase p " +
            "join fetch p.warehouse w " +
            "join fetch p.vendor v " +
            "join fetch p.tireDot d " +
            "join fetch d.tire t " +
            "join fetch t.brand b " +
            "where p.id = :id")
    Optional<Purchase> findOneFetchAllById(@Param("id") Long id);

    @EntityGraph(attributePaths = {"tireDot"})
    Optional<Purchase> findOneFetchTireDotById(Long id);
}
