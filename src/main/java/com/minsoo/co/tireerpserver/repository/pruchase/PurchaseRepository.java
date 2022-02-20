package com.minsoo.co.tireerpserver.repository.pruchase;

import com.minsoo.co.tireerpserver.entity.purchase.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
