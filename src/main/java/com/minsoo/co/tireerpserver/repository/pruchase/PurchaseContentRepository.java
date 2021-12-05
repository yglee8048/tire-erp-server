package com.minsoo.co.tireerpserver.repository.pruchase;

import com.minsoo.co.tireerpserver.entity.purchase.PurchaseContent;
import com.minsoo.co.tireerpserver.entity.tire.Tire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PurchaseContentRepository extends JpaRepository<PurchaseContent, Long> {

    @Query("select avg(pc.price) from PurchaseContent pc join pc.tireDot td join td.tire t where t = :tire")
    Double averagePriceByTire(Tire tire);
}
