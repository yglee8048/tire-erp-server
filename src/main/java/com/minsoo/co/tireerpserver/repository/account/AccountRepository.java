package com.minsoo.co.tireerpserver.repository.account;

import com.minsoo.co.tireerpserver.entity.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
