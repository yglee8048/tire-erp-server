package com.minsoo.co.tireerpserver.account.repository;

import com.minsoo.co.tireerpserver.account.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
