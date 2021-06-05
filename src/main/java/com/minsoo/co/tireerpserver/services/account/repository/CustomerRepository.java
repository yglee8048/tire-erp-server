package com.minsoo.co.tireerpserver.services.account.repository;

import com.minsoo.co.tireerpserver.services.account.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
