package com.minsoo.co.tireerpserver.model.entity;

import com.minsoo.co.tireerpserver.model.entity.embedded.BusinessInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "customer_id", length = 20, nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Embedded
    private BusinessInfo businessInfo;
}
