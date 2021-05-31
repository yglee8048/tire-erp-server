package com.minsoo.co.tireerpserver.model.entity.stock;

import com.minsoo.co.tireerpserver.api.error.exceptions.NotEnoughStockException;
import com.minsoo.co.tireerpserver.model.entity.management.Warehouse;
import com.minsoo.co.tireerpserver.model.entity.tire.TireDot;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "stock",
        uniqueConstraints = {@UniqueConstraint(name = "stock_unique_dot_nickname", columnNames = {"tire_dot_id", "nickname"})})
public class Stock {

    @Id
    @Column(name = "stock_id", length = 20)
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "tire_dot_id", nullable = false)
    private TireDot tireDot;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "quantity", nullable = false)
    private Long quantity;

    @Column(name = "is_lock", nullable = false)
    private boolean lock;

    //== Business ==//
    public Stock(TireDot tireDot, Warehouse warehouse, String nickname, Long quantity, Boolean lock) {
        this.tireDot = tireDot;
        this.warehouse = warehouse;
        this.nickname = nickname;
        this.quantity = quantity;
        this.lock = lock;
    }

    public static Stock of(TireDot tireDot, Warehouse warehouse, String nickname, Long quantity, Boolean lock) {
        return new Stock(tireDot, warehouse, nickname, quantity, lock);
    }

    public Stock update(TireDot tireDot, Warehouse warehouse, String nickname, Long quantity, Boolean lock) {
        this.tireDot = tireDot;
        this.warehouse = warehouse;
        this.nickname = nickname;
        this.quantity = quantity;
        this.lock = lock;

        return this;
    }

    public void addQuantity(Long quantity) {
        this.quantity += quantity;
    }

    public void reduceQuantity(Long quantity) {
        if (this.quantity < quantity) {
            throw new NotEnoughStockException();
        } else {
            this.quantity -= quantity;
        }
    }

    public void removeFromTireDot() {
        this.tireDot.getStocks().remove(this);
        this.tireDot = null;
    }
}
