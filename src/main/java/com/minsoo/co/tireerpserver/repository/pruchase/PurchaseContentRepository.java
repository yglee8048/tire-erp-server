package com.minsoo.co.tireerpserver.repository.pruchase;

import com.minsoo.co.tireerpserver.entity.purchase.Purchase;
import com.minsoo.co.tireerpserver.entity.purchase.PurchaseContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseContentRepository extends JpaRepository<PurchaseContent, Long> {

    List<PurchaseContent> findAllByPurchase(Purchase purchase);
}
