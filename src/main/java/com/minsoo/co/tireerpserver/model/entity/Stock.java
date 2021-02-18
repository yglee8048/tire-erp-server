package com.minsoo.co.tireerpserver.model.entity;

import com.minsoo.co.tireerpserver.api.error.errors.NotEnoughStockException;
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
        uniqueConstraints = {@UniqueConstraint(name = "stock_unique_dot_warehouse", columnNames = {"tire_dot_id", "warehouse_id"})})
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

    @Column(name = "quantity", nullable = false)
    private Long quantity;

    @Column(name = "lock", nullable = false)
    private boolean lock;

    //== Business ==//
    private Stock(TireDot tireDot, Warehouse warehouse, boolean lock) {
        this.tireDot = tireDot;
        this.warehouse = warehouse;
        this.quantity = 0L;
        this.lock = lock;
    }

    public static Stock of(TireDot tireDot, Warehouse warehouse, boolean lock) {
        return new Stock(tireDot, warehouse, lock);
    }

    public void updateLock(boolean lock) {
        this.lock = lock;
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
}
