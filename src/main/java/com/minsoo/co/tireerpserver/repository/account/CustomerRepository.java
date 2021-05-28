package com.minsoo.co.tireerpserver.repository.account;

import com.minsoo.co.tireerpserver.model.entity.account.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    boolean existsByUserId(String userId);
}
