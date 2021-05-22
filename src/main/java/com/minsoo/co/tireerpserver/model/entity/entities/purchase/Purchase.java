package com.minsoo.co.tireerpserver.model.entity.entities.purchase;

import com.minsoo.co.tireerpserver.model.code.PurchaseStatus;
import com.minsoo.co.tireerpserver.model.dto.purchase.PurchaseCreateRequestContent;
import com.minsoo.co.tireerpserver.model.dto.purchase.PurchaseUpdateRequest;
import com.minsoo.co.tireerpserver.model.entity.entities.tire.TireDot;
import com.minsoo.co.tireerpserver.model.entity.entities.management.Vendor;
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

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "quantity", nullable = false)
    private Long quantity;

    @Enumerated(STRING)
    @Column(name = "status", nullable = false)
    private PurchaseStatus status;

    @Column(name = "purchase_date", nullable = false)
    private LocalDate purchaseDate;

    //== Business ==//
    private Purchase(Vendor vendor, TireDot tireDot, PurchaseCreateRequestContent createRequest, LocalDate purchaseDate) {
        this.vendor = vendor;
        this.tireDot = tireDot;
        this.price = createRequest.getPrice();
        this.quantity = createRequest.getQuantity();
        this.status = PurchaseStatus.REQUESTED;
        this.purchaseDate = purchaseDate;
    }

    public static Purchase of(Vendor vendor, TireDot tireDot, PurchaseCreateRequestContent createRequest, LocalDate purchaseDate) {
        return new Purchase(vendor, tireDot, createRequest, purchaseDate);
    }

    public void update(Vendor vendor, TireDot tireDot, PurchaseUpdateRequest updateRequest) {
        this.vendor = vendor;
        this.tireDot = tireDot;
        this.price = updateRequest.getPrice();
        this.quantity = updateRequest.getQuantity();
        this.purchaseDate = updateRequest.getPurchaseDate();
    }

    public void confirm() {
        this.status = PurchaseStatus.CONFIRMED;
    }
}
