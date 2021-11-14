package com.minsoo.co.tireerpserver.purchase.repository;

import com.minsoo.co.tireerpserver.purchase.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    List<Purchase> findByTransactionDateBetween(LocalDate start, LocalDate end);
}
