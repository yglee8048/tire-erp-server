package com.minsoo.co.tireerpserver.user.repository;

import com.minsoo.co.tireerpserver.user.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByUsername(String username);
}
