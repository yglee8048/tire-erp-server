package com.minsoo.co.tireerpserver.repository;

import com.minsoo.co.tireerpserver.model.entity.Tire;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TireRepository extends JpaRepository<Tire, Long> {

    @EntityGraph(attributePaths = {"brand"})
    @Query("select t from Tire t")
    List<Tire> findAllFetchBrand();

    @EntityGraph(attributePaths = {"brand"})
    Optional<Tire> findOneFetchBrandById(Long id);

    @Query("select distinct t.size from Tire t")
    List<String> findAllSizes();

    @Query("select t.productId from Tire t")
    List<String> findAllProductIds();

    @Query("select distinct t.pattern from Tire t")
    List<String> findAllPatterns();

    boolean existsByProductId(String productId);
}
