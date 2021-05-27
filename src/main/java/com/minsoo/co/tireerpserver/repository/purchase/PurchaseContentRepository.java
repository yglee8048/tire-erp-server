package com.minsoo.co.tireerpserver.repository.purchase;

import com.minsoo.co.tireerpserver.model.entity.entities.purchase.Purchase;
import com.minsoo.co.tireerpserver.model.entity.entities.purchase.PurchaseContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseContentRepository extends JpaRepository<PurchaseContent, Long> {

    List<PurchaseContent> findAllByPurchase(Purchase purchase);
}
