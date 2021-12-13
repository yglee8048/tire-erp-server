package com.minsoo.co.tireerpserver.model.response.sale;

import com.minsoo.co.tireerpserver.entity.sale.Delivery;
import com.minsoo.co.tireerpserver.model.AddressDTO;
import com.minsoo.co.tireerpserver.model.request.sale.DeliveryOption;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeliveryResponse {

    private Long deliveryId;
    private Long saleId;
    private String recipientName;
    private AddressDTO address;
    private String recipientPhoneNumber;
    private DeliveryOption deliveryOption;
    private String deliveryCompany;
    private String invoiceNumber;

    public DeliveryResponse(Delivery delivery) {
        this.deliveryId = delivery.getId();
        this.saleId = delivery.getSale().getId();
        this.recipientName = delivery.getRecipientName();
        this.address = new AddressDTO(delivery.getAddress());
        this.recipientPhoneNumber = delivery.getRecipientPhoneNumber();
        this.deliveryOption = delivery.getDeliveryOption();
        this.deliveryCompany = delivery.getDeliveryCompany();
        this.invoiceNumber = delivery.getInvoiceNumber();
    }
}
