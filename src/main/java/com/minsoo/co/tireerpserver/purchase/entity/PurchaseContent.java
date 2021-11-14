package com.minsoo.co.tireerpserver.purchase.entity;

import com.minsoo.co.tireerpserver.management.entity.Warehouse;
import com.minsoo.co.tireerpserver.tire.entity.TireDot;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "purchase_content",
        uniqueConstraints = {@UniqueConstraint(name = "purchase_content_tire_dot_unique", columnNames = {"purchase_id", "tire_dot_id"})})
public class PurchaseContent {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "purchase_content_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "purchase_id", referencedColumnName = "purchase_id", nullable = false)
    private Purchase purchase;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "tire_dot_id", nullable = false)
    private TireDot tireDot;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "quantity", nullable = false)
    private Long quantity;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "warehouse_id", referencedColumnName = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    //== Business ==//
    public PurchaseContent(Purchase purchase) {
        this.purchase = purchase;
    }

    public static PurchaseContent of(Purchase purchase, TireDot tireDot, Integer price, Long quantity, Warehouse warehouse) {
        PurchaseContent purchaseContent = new PurchaseContent(purchase);
        purchaseContent.update(tireDot, price, quantity, warehouse);
        return purchaseContent;
    }

    public PurchaseContent update(TireDot tireDot, Integer price, Long quantity, Warehouse warehouse) {
        this.tireDot = tireDot;
        this.price = price;
        this.quantity = quantity;
        this.warehouse = warehouse;

        return this;
    }

    public void removeFromPurchase() {
        this.purchase.getContents().remove(this);
        this.purchase = null;
    }
}
