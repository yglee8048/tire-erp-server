package com.minsoo.co.tireerpserver.repository.client;

import com.minsoo.co.tireerpserver.entity.client.Client;
import com.minsoo.co.tireerpserver.entity.client.ClientCompany;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findAllByClientCompany(ClientCompany clientCompany);

    Optional<Client> findByUsername(String username);
}
