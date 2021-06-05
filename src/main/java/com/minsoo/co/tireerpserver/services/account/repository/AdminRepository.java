package com.minsoo.co.tireerpserver.services.account.repository;

import com.minsoo.co.tireerpserver.services.account.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    boolean existsByUserId(String userId);
}
