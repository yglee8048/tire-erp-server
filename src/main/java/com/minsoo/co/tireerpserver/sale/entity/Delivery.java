package com.minsoo.co.tireerpserver.sale.entity;

import com.minsoo.co.tireerpserver.sale.model.delivery.DeliveryRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "delivery")
public class Delivery {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "delivery_id", length = 20, nullable = false)
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = LAZY)
    private Sale sale;

    @Embedded
    private Recipient recipient;

    @Column(name = "delivery_company")
    private String deliveryCompany;

    @Column(name = "invoice_number")
    private Integer invoiceNumber;

    public Delivery(Sale sale, DeliveryRequest deliveryRequest) {
        this.sale = sale;
        this.recipient = Recipient.of(deliveryRequest);
        this.deliveryCompany = deliveryRequest.getDeliveryCompany();
        this.invoiceNumber = deliveryRequest.getInvoiceNumber();
    }

    public static Delivery of(Sale sale, DeliveryRequest deliveryRequest) {
        return new Delivery(sale, deliveryRequest);
    }

    public Delivery update(Sale sale, DeliveryRequest deliveryRequest) {
        this.sale = sale;
        this.recipient = Recipient.of(deliveryRequest);
        this.deliveryCompany = deliveryRequest.getDeliveryCompany();
        this.invoiceNumber = deliveryRequest.getInvoiceNumber();

        return this;
    }
}
