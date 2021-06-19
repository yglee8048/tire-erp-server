package com.minsoo.co.tireerpserver.sale.entity;

import com.minsoo.co.tireerpserver.sale.model.delivery.DeliveryRequest;
import com.minsoo.co.tireerpserver.shared.entity.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Embeddable
public class Recipient {

    @Column(name = "recipient_name")
    private String name;

    @Embedded
    private Address address;

    @Column(name = "recipient_phone_number")
    private String phoneNumber;

    public Recipient(DeliveryRequest deliveryRequest) {
        this.name = deliveryRequest.getRecipientName();
        this.address = Address.of(deliveryRequest.getRecipientAddress());
        this.phoneNumber = deliveryRequest.getRecipientPhoneNumber();
    }

    public static Recipient of(DeliveryRequest deliveryRequest) {
        if (deliveryRequest == null) {
            return new Recipient();
        }
        return new Recipient(deliveryRequest);
    }
}
