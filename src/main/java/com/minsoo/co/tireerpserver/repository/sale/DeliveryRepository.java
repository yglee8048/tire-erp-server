package com.minsoo.co.tireerpserver.repository.sale;

import com.minsoo.co.tireerpserver.entity.sale.Delivery;
import com.minsoo.co.tireerpserver.entity.sale.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    Optional<Delivery> findBySale(Sale sale);
}
