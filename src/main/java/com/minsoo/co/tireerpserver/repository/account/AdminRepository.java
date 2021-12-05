package com.minsoo.co.tireerpserver.repository.account;

import com.minsoo.co.tireerpserver.entity.account.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
