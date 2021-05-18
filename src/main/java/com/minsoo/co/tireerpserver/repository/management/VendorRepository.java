package com.minsoo.co.tireerpserver.repository.management;

import com.minsoo.co.tireerpserver.model.entity.entities.management.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, Long> {

    boolean existsByName(String name);
}
