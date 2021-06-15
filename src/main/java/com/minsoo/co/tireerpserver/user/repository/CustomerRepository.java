package com.minsoo.co.tireerpserver.user.repository;

import com.minsoo.co.tireerpserver.user.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
