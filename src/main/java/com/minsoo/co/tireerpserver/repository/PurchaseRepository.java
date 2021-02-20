package com.minsoo.co.tireerpserver.repository;

import com.minsoo.co.tireerpserver.model.entity.Purchase;
import com.minsoo.co.tireerpserver.model.entity.TireDot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("delete from Purchase p where p.tireDot in :tireDots")
    void deleteAllByTireDotIn(@Param("tireDots") List<TireDot> tireDots);
}
