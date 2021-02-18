package com.minsoo.co.tireerpserver.model.entity;

import com.minsoo.co.tireerpserver.model.code.PurchaseStatus;
import com.minsoo.co.tireerpserver.model.dto.purchase.PurchaseRequestContent;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDate;

import static javax.persistence.EnumType.*;
import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "purchase")
public class Purchase {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "purchase_id", length = 20, nullable = false)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor vendor;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "tire_dot_id", nullable = false)
    private TireDot tireDot;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Enumerated(STRING)
    @Column(name = "status", nullable = false)
    private PurchaseStatus status;

    @Column(name = "purchased_date", nullable = false)
    private LocalDate purchasedDate;

    private Purchase(Vendor vendor, TireDot tireDot, Warehouse warehouse, PurchaseRequestContent createRequest, LocalDate purchasedDate) {
        this.vendor = vendor;
        this.tireDot = tireDot;
        this.warehouse = warehouse;
        this.price = createRequest.getPrice();
        this.quantity = createRequest.getQuantity();
        this.status = PurchaseStatus.REQUESTED;
        this.purchasedDate = purchasedDate;
    }

    public static Purchase of(Vendor vendor, TireDot tireDot, Warehouse warehouse, PurchaseRequestContent createRequest, LocalDate purchasedDate) {
        return new Purchase(vendor, tireDot, warehouse, createRequest, purchasedDate);
    }
}
