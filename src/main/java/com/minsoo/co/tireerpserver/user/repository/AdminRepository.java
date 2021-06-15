package com.minsoo.co.tireerpserver.user.repository;

import com.minsoo.co.tireerpserver.user.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
