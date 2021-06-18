package com.minsoo.co.tireerpserver.user.repository;

import com.minsoo.co.tireerpserver.user.entity.Client;
import com.minsoo.co.tireerpserver.user.entity.ClientCompany;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findAllByClientCompany(ClientCompany clientCompany);
}
