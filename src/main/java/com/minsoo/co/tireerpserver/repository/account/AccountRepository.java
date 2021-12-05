package com.minsoo.co.tireerpserver.repository.account;

import com.minsoo.co.tireerpserver.entity.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByUsername(String username);
}
