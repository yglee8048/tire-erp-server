package com.minsoo.co.tireerpserver.services.sale.entity;

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
}
