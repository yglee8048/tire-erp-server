package com.minsoo.co.tireerpserver.sale.service;

import com.minsoo.co.tireerpserver.sale.code.SaleStatus;
import com.minsoo.co.tireerpserver.sale.entity.Delivery;
import com.minsoo.co.tireerpserver.sale.entity.Sale;
import com.minsoo.co.tireerpserver.sale.model.delivery.DeliveryRequest;
import com.minsoo.co.tireerpserver.sale.repository.DeliveryRepository;
import com.minsoo.co.tireerpserver.sale.repository.SaleRepository;
import com.minsoo.co.tireerpserver.shared.error.exceptions.InvalidStateException;
import com.minsoo.co.tireerpserver.shared.error.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final SaleRepository saleRepository;

    public List<Delivery> findBySaleId(Long saleId) {
        Sale sale = saleRepository.findById(saleId).orElseThrow(() -> NotFoundException.of("매출"));
        return deliveryRepository.findAllBySale(sale);
    }

    public Delivery findById(Long id) {
        return deliveryRepository.findById(id).orElseThrow(() -> NotFoundException.of("배송"));
    }

    public Delivery create(Long saleId, DeliveryRequest deliveryRequest) {
        Sale sale = saleRepository.findById(saleId).orElseThrow(() -> NotFoundException.of("매출"));
        return deliveryRepository.save(Delivery.of(sale, deliveryRequest));
    }

    public Delivery update(Long saleId, Long deliveryId, DeliveryRequest deliveryRequest) {
        Sale sale = saleRepository.findById(saleId).orElseThrow(() -> NotFoundException.of("매출"));
        Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow(() -> NotFoundException.of("배송"));
        return delivery.update(sale, deliveryRequest);
    }

    public void removeById(Long id) {
        Delivery delivery = deliveryRepository.findById(id).orElseThrow(() -> NotFoundException.of("배송"));
        deliveryRepository.delete(delivery);
    }
}
