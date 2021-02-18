package com.minsoo.co.tireerpserver.repository;

import com.minsoo.co.tireerpserver.model.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
