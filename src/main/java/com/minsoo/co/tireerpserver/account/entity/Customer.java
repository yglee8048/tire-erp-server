package com.minsoo.co.tireerpserver.account.entity;

import com.minsoo.co.tireerpserver.model.dto.account.customer.CustomerRequest;
import com.minsoo.co.tireerpserver.model.entity.BusinessInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "customer",
        uniqueConstraints = {@UniqueConstraint(name = "customer_unique_user_id", columnNames = {"user_id"})})
public class Customer {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "customer_id", length = 20, nullable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "user_pw", nullable = false)
    private String userPw;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Embedded
    private BusinessInfo businessInfo;

    private Customer(CustomerRequest createRequest) {
        this.userId = createRequest.getUserId();
        this.userPw = createRequest.getUserPw();
        this.name = createRequest.getName();
        this.description = createRequest.getDescription();
        this.businessInfo = BusinessInfo.of(createRequest.getBusinessInfo());
    }

    public static Customer of(CustomerRequest createRequest) {
        return new Customer(createRequest);
    }

    public void update(CustomerRequest updateRequest) {
        this.userId = updateRequest.getUserId();
        if (userPw != null) {
            this.userPw = updateRequest.getUserPw();
        }
        this.name = updateRequest.getName();
        this.description = updateRequest.getDescription();
        this.businessInfo = BusinessInfo.of(updateRequest.getBusinessInfo());
    }
}
