package com.minsoo.co.tireerpserver.repository.account;

import com.minsoo.co.tireerpserver.entity.account.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
