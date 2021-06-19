package com.minsoo.co.tireerpserver.sale.repository;

import com.minsoo.co.tireerpserver.sale.entity.Delivery;
import com.minsoo.co.tireerpserver.sale.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    List<Delivery> findAllBySale(Sale sale);
}
