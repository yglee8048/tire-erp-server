package com.minsoo.co.tireerpserver.entity.purchase;

import com.minsoo.co.tireerpserver.entity.BaseTimeEntity;
import com.minsoo.co.tireerpserver.entity.management.Warehouse;
import com.minsoo.co.tireerpserver.entity.tire.TireDot;
import com.minsoo.co.tireerpserver.model.request.purchase.PurchaseContentRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "purchase_content")
public class PurchaseContent extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_content_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_id", referencedColumnName = "purchase_id")
    private Purchase purchase;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "tire_dot_id", nullable = false)
    private TireDot tireDot;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "quantity", nullable = false)
    private Long quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", referencedColumnName = "warehouse_id")
    private Warehouse warehouse;

    public PurchaseContent(Purchase purchase) {
        this.purchase = purchase;
    }

    public static PurchaseContent of(Purchase purchase, TireDot tireDot, Warehouse warehouse, PurchaseContentRequest purchaseContentRequest) {
        PurchaseContent purchaseContent = new PurchaseContent(purchase);
        return purchaseContent.update(tireDot, warehouse, purchaseContentRequest);
    }

    public PurchaseContent update(TireDot tireDot, Warehouse warehouse, PurchaseContentRequest purchaseContentRequest) {
        this.tireDot = tireDot;
        this.price = purchaseContentRequest.getPrice();
        this.quantity = purchaseContentRequest.getQuantity();
        this.warehouse = warehouse;
        return this;
    }
}
