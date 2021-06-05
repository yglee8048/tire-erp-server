package com.minsoo.co.tireerpserver.services.purchase.repository;

import com.minsoo.co.tireerpserver.services.purchase.entity.Purchase;
import com.minsoo.co.tireerpserver.services.purchase.entity.PurchaseContent;
import com.minsoo.co.tireerpserver.services.purchase.repository.query.PurchaseContentQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseContentRepository extends JpaRepository<PurchaseContent, Long>, PurchaseContentQueryRepository {

    List<PurchaseContent> findAllByPurchase(Purchase purchase);
}
