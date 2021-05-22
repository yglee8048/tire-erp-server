package com.minsoo.co.tireerpserver.repository.purchase;

import com.minsoo.co.tireerpserver.model.entity.entities.purchase.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
