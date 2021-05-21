package com.minsoo.co.tireerpserver.model.entity.entities.stock;

import com.minsoo.co.tireerpserver.api.error.exceptions.NotEnoughStockException;
import com.minsoo.co.tireerpserver.model.dto.stock.ModifyStockRequest;
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
        uniqueConstraints = {@UniqueConstraint(name = "stock_unique_dot_warehouse_nickname", columnNames = {"tire_dot_id", "warehouse_id", "nickname"})})
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
    private Stock(TireDot tireDot, Warehouse warehouse, ModifyStockRequest modifyStockRequest) {
        this.tireDot = tireDot;
        this.nickname = modifyStockRequest.getNickname();
        this.warehouse = warehouse;
        this.quantity = modifyStockRequest.getQuantity();
        this.lock = modifyStockRequest.isLock();
    }

    public static Stock of(TireDot tireDot, Warehouse warehouse, ModifyStockRequest modifyStockRequest) {
        return new Stock(tireDot, warehouse, modifyStockRequest);
    }

    public Stock update(TireDot tireDot, Warehouse warehouse, ModifyStockRequest modifyStockRequest) {
        this.tireDot = tireDot;
        this.nickname = modifyStockRequest.getNickname();
        this.warehouse = warehouse;
        this.quantity = modifyStockRequest.getQuantity();
        this.lock = modifyStockRequest.isLock();

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
