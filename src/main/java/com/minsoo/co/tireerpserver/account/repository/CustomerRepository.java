package com.minsoo.co.tireerpserver.account.repository;

import com.minsoo.co.tireerpserver.account.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
