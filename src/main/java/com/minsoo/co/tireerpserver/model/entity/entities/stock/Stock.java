package com.minsoo.co.tireerpserver.model.entity.entities.stock;

import com.minsoo.co.tireerpserver.api.error.exceptions.NotEnoughStockException;
import com.minsoo.co.tireerpserver.model.entity.entities.management.Warehouse;
import com.minsoo.co.tireerpserver.model.entity.entities.tire.TireDot;
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
        uniqueConstraints = {@UniqueConstraint(name = "stock_unique_dot_warehouse", columnNames = {"nickname", "tire_dot_id", "warehouse_id"})})
public class Stock {

    @Id
    @Column(name = "stock_id", length = 20)
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "tire_dot_id", nullable = false)
    private TireDot tireDot;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @Column(name = "quantity", nullable = false)
    private Long quantity;

    @Column(name = "is_lock", nullable = false)
    private boolean lock;

    //== Business ==//
    private Stock(TireDot tireDot, String nickname, Warehouse warehouse, Long quantity, boolean lock) {
        this.tireDot = tireDot;
        this.nickname = nickname;
        this.warehouse = warehouse;
        this.quantity = quantity;
        this.lock = lock;
    }

    public static Stock of(TireDot tireDot, String nickname, Warehouse warehouse, Long quantity, boolean lock) {
        return new Stock(tireDot, nickname, warehouse, quantity, lock);
    }

    public Stock update(TireDot tireDot, String nickname, Warehouse warehouse, Long quantity, boolean lock) {
        this.tireDot = tireDot;
        this.nickname = nickname;
        this.warehouse = warehouse;
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
}
