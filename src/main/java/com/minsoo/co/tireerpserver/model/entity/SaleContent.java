package com.minsoo.co.tireerpserver.model.entity;

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

    @Column(name = "quantity", nullable = false)
    private Long quantity;

    @Column(name = "price", nullable = false)
    private Integer price;

    //== Business ==//
    private SaleContent(Sale sale, TireDot tireDot, Long quantity, Integer price) {
        this.sale = sale;
        sale.getSaleContents().add(this);
        this.tireDot = tireDot;
        this.quantity = quantity;
        this.price = price;
    }

    public static SaleContent of(Sale sale, TireDot tireDot, Long quantity, Integer price) {
        return new SaleContent(sale, tireDot, quantity, price);
    }
}
