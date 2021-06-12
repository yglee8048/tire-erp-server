package com.minsoo.co.tireerpserver.management.repository;

import com.minsoo.co.tireerpserver.management.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, Long> {

    boolean existsByName(String name);
}
