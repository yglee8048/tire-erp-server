package com.minsoo.co.tireerpserver.repository.account;

import com.minsoo.co.tireerpserver.entity.account.Client;
import com.minsoo.co.tireerpserver.entity.account.ClientCompany;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findAllByClientCompany(ClientCompany clientCompany);
}
