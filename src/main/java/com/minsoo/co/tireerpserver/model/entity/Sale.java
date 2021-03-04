package com.minsoo.co.tireerpserver.model.entity;

import com.minsoo.co.tireerpserver.model.code.SaleSource;
import com.minsoo.co.tireerpserver.model.code.SaleStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.*;
import static javax.persistence.EnumType.*;
import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "sale")
public class Sale {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "sale_id", length = 20, nullable = false)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Enumerated(STRING)
    @Column(name = "source", nullable = false)
    private SaleSource source;

    @Enumerated(STRING)
    @Column(name = "status", nullable = false)
    private SaleStatus status;

    @OneToOne(fetch = LAZY, cascade = ALL, orphanRemoval = true)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @Column(name = "sale_date", nullable = false)
    private LocalDate saleDate;

    @OneToMany(mappedBy = "sale", fetch = LAZY, cascade = ALL, orphanRemoval = true)
    private Set<SaleContent> saleContents = new HashSet<>();

    @OneToMany(mappedBy = "sale", fetch = LAZY, cascade = ALL, orphanRemoval = true)
    private Set<SaleMemo> saleMemos = new HashSet<>();

    //== Business ==//
    public Sale(Customer customer, LocalDate saleDate) {
        this.customer = customer;
        this.source = SaleSource.MANUAL;
        this.status = SaleStatus.REQUESTED;
        this.delivery = null;
        this.saleDate = saleDate;
    }

    public static Sale of(Customer customer, LocalDate saleDate) {
        return new Sale(customer, saleDate);
    }
}
