package com.minsoo.co.tireerpserver.services.purchase.repository;

import com.minsoo.co.tireerpserver.services.purchase.entity.Purchase;
import com.minsoo.co.tireerpserver.services.purchase.repository.query.PurchaseQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long>, PurchaseQueryRepository {
}
