package com.minsoo.co.tireerpserver.purchase.repository;

import com.minsoo.co.tireerpserver.purchase.repository.query.PurchaseQueryRepository;
import com.minsoo.co.tireerpserver.purchase.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long>, PurchaseQueryRepository {
}
