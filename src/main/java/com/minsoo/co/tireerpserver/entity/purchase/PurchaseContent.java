package com.minsoo.co.tireerpserver.entity.purchase;

import com.minsoo.co.tireerpserver.entity.BaseEntity;
import com.minsoo.co.tireerpserver.entity.stock.Stock;
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
public class PurchaseContent extends BaseEntity {

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id", referencedColumnName = "stock_id")
    private Stock stock;

    @Column(name = "price", nullable = false)
    private Long price;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    private PurchaseContent(Purchase purchase, TireDot tireDot, Stock stock) {
        this.purchase = purchase;
        this.purchase.getPurchaseContents().add(this);
        this.tireDot = tireDot;
        this.stock = stock;
    }

    public static PurchaseContent of(Purchase purchase, TireDot tireDot, Stock stock, PurchaseContentRequest purchaseContentRequest) {
        PurchaseContent purchaseContent = new PurchaseContent(purchase, tireDot, stock);
        return purchaseContent.update(purchaseContentRequest);
    }

    public PurchaseContent update(PurchaseContentRequest purchaseContentRequest) {
        this.price = purchaseContentRequest.getPrice();

        this.stock.addQuantity(purchaseContentRequest.getQuantity() - this.quantity);   // 재고 변경 바로 적용
        this.quantity = purchaseContentRequest.getQuantity();

        return this;
    }
}
