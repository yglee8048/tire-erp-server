package com.minsoo.co.tireerpserver.services.account.entity;

import com.minsoo.co.tireerpserver.api.v1.model.dto.account.customer.CustomerRequest;
import com.minsoo.co.tireerpserver.services.shared.entity.BusinessInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "customer_id", length = 20, nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Embedded
    private BusinessInfo businessInfo;

    private Customer(CustomerRequest createRequest) {
        this.name = createRequest.getName();
        this.description = createRequest.getDescription();
        this.businessInfo = BusinessInfo.of(createRequest.getBusinessInfo());
    }

    public static Customer of(CustomerRequest createRequest) {
        return new Customer(createRequest);
    }

    public void update(CustomerRequest updateRequest) {
        this.name = updateRequest.getName();
        this.description = updateRequest.getDescription();
        this.businessInfo = BusinessInfo.of(updateRequest.getBusinessInfo());
    }
}
