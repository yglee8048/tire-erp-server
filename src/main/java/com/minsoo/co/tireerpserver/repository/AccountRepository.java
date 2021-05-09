package com.minsoo.co.tireerpserver.repository;

import com.minsoo.co.tireerpserver.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByUserId(String userId);

    boolean existsByUserId(String userId);
}
