package com.minsoo.co.tireerpserver.service.sale;

import com.minsoo.co.tireerpserver.entity.sale.Delivery;
import com.minsoo.co.tireerpserver.entity.sale.Sale;
import com.minsoo.co.tireerpserver.exception.InternalServerException;
import com.minsoo.co.tireerpserver.exception.NotFoundException;
import com.minsoo.co.tireerpserver.model.request.sale.DeliveryRequest;
import com.minsoo.co.tireerpserver.model.response.sale.DeliveryResponse;
import com.minsoo.co.tireerpserver.repository.sale.DeliveryRepository;
import com.minsoo.co.tireerpserver.repository.sale.SaleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final SaleRepository saleRepository;

    public DeliveryResponse findBySaleId(Long saleId) {
        return new DeliveryResponse(findDeliveryBySaleId(saleId));
    }

    public DeliveryResponse update(Long saleId, DeliveryRequest deliveryRequest) {
        Delivery delivery = findDeliveryBySaleId(saleId);
        return new DeliveryResponse(delivery.update(deliveryRequest));
    }

    private Sale findSaleById(Long saleId) {
        return saleRepository.findById(saleId).orElseThrow(() -> new NotFoundException("매출", saleId));
    }

    private Delivery findDeliveryBySaleId(Long saleId) {
        Sale sale = findSaleById(saleId);
        return deliveryRepository.findBySale(sale).orElseThrow(() -> {
            log.error("The delivery does not exist for sale: {}", saleId);
            return new InternalServerException();
        });
    }
}
