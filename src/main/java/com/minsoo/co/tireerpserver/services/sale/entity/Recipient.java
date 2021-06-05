package com.minsoo.co.tireerpserver.services.sale.entity;

import com.minsoo.co.tireerpserver.services.shared.entity.Address;
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

    @Column(name = "recipient")
    private String recipient;

    @Embedded
    private Address recipientAddress;

    @Column(name = "recipient_phone_number")
    private String recipientPhoneNumber;
}