package com.minsoo.co.tireerpserver.services.sale.entity;

import com.minsoo.co.tireerpserver.services.tire.entity.TireDot;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "sale_content")
public class SaleContent {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "sale_content_id", length = 20, nullable = false)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "sale_id", nullable = false)
    private Sale sale;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "tire_dot_id", nullable = false)
    private TireDot tireDot;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "quantity", nullable = false)
    private Long quantity;

    //== Business ==//
    private SaleContent(Sale sale, TireDot tireDot, Integer price, Long quantity) {
        this.sale = sale;
        sale.getContents().add(this);
        this.tireDot = tireDot;
        this.price = price;
        this.quantity = quantity;
    }

    public static SaleContent of(Sale sale, TireDot tireDot, Integer price, Long quantity) {
        return new SaleContent(sale, tireDot, price, quantity);
    }

    public SaleContent update(TireDot tireDot, Integer price, Long quantity) {
        this.tireDot = tireDot;
        this.price = price;
        this.quantity = quantity;

        return this;
    }

    public void removeFromSale() {
        this.sale.getContents().remove(this);
        this.sale = null;
    }
}
