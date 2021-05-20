package com.minsoo.co.tireerpserver.repository.tire;

import com.minsoo.co.tireerpserver.model.entity.entities.tire.Pattern;
import com.minsoo.co.tireerpserver.model.entity.entities.tire.Tire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TireRepository extends JpaRepository<Tire, Long> {

    @Query("select distinct t.size from Tire t")
    List<String> findAllSizes();

    @Query("select distinct t.productId from Tire t")
    List<String> findAllProductIds();

    boolean existsByProductId(String productId);

    boolean existsByPattern(Pattern pattern);
}
