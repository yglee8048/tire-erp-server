package com.minsoo.co.tireerpserver.entity.sale;

import com.minsoo.co.tireerpserver.entity.Address;
import com.minsoo.co.tireerpserver.entity.BaseTimeEntity;
import com.minsoo.co.tireerpserver.model.request.sale.DeliveryOption;
import com.minsoo.co.tireerpserver.model.request.sale.DeliveryRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "delivery")
public class Delivery extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Sale sale;

    @Column(name = "recipient_name")
    private String recipientName;

    @Embedded
    private Address address;

    @Column(name = "recipient_phone_number")
    private String recipientPhoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_option")
    private DeliveryOption deliveryOption;

    @Column(name = "delivery_company")
    private String deliveryCompany;

    @Column(name = "invoice_number")
    private String invoiceNumber;

    public Delivery update(DeliveryRequest deliveryRequest) {
        this.recipientName = deliveryRequest.getRecipientName();
        this.address = new Address(deliveryRequest.getAddress());
        this.recipientPhoneNumber = deliveryRequest.getRecipientPhoneNumber();
        this.deliveryOption = deliveryRequest.getDeliveryOption();
        this.deliveryCompany = deliveryRequest.getDeliveryCompany();
        this.invoiceNumber = deliveryRequest.getInvoiceNumber();
        return this;
    }
}
