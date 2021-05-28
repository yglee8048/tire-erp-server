package com.minsoo.co.tireerpserver.repository.purchase;

import com.minsoo.co.tireerpserver.model.entity.purchase.Purchase;
import com.minsoo.co.tireerpserver.repository.query.PurchaseQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long>, PurchaseQueryRepository {
}
