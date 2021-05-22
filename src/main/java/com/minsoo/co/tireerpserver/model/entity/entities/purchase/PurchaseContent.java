package com.minsoo.co.tireerpserver.model.entity.entities.purchase;

import com.minsoo.co.tireerpserver.model.dto.purchase.CreatePurchaseContentRequest;
import com.minsoo.co.tireerpserver.model.dto.purchase.UpdatePurchaseContentRequest;
import com.minsoo.co.tireerpserver.model.entity.entities.tire.TireDot;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "purchase_content")
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

    public PurchaseContent(Purchase purchase, TireDot tireDot, Integer price, Long quantity) {
        this.purchase = purchase;
        this.tireDot = tireDot;
        this.price = price;
        this.quantity = quantity;
    }

    public static PurchaseContent of(Purchase purchase, TireDot tireDot, CreatePurchaseContentRequest createRequest) {
        return new PurchaseContent(purchase, tireDot, createRequest.getPrice(), createRequest.getQuantity());
    }

    public PurchaseContent update(TireDot tireDot, UpdatePurchaseContentRequest updateRequest) {
        this.tireDot = tireDot;
        this.price = updateRequest.getPrice();
        this.quantity = updateRequest.getQuantity();

        return this;
    }
}
