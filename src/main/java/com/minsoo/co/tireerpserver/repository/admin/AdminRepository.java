package com.minsoo.co.tireerpserver.repository.admin;

import com.minsoo.co.tireerpserver.entity.admin.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
