package com.minsoo.co.tireerpserver.repository.pruchase;

import com.minsoo.co.tireerpserver.entity.purchase.Purchase;
import com.minsoo.co.tireerpserver.entity.purchase.PurchaseContent;
import com.minsoo.co.tireerpserver.entity.stock.Stock;
import com.minsoo.co.tireerpserver.entity.tire.TireDot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PurchaseContentRepository extends JpaRepository<PurchaseContent, Long> {

    List<PurchaseContent> findAllByPurchase(Purchase purchase);

    Optional<PurchaseContent> findByPurchaseAndTireDotAndStock(Purchase purchase, TireDot tireDot, Stock stock);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("delete from PurchaseContent c where c.id in :ids")
    void deleteAllByIdIn(List<Long> ids);
}
