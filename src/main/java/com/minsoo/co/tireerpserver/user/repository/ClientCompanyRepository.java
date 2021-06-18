package com.minsoo.co.tireerpserver.user.repository;

import com.minsoo.co.tireerpserver.user.entity.ClientCompany;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientCompanyRepository extends JpaRepository<ClientCompany, Long> {

    boolean existsByName(String name);
}
