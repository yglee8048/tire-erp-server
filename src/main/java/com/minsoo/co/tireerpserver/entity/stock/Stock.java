package com.minsoo.co.tireerpserver.entity.stock;

import com.minsoo.co.tireerpserver.entity.BaseTimeEntity;
import com.minsoo.co.tireerpserver.entity.management.Warehouse;
import com.minsoo.co.tireerpserver.entity.tire.TireDot;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "stock")
public class Stock extends BaseTimeEntity {

    @Id
    @Column(name = "stock_id", length = 20)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tire_dot_id", nullable = false)
    private TireDot tireDot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "quantity", nullable = false)
    private Long quantity;

    @Column(name = "is_lock", nullable = false)
    private Boolean lock;

    public Stock(TireDot tireDot, String nickname) {
        this.tireDot = tireDot;
        this.nickname = nickname;
    }

    public static Stock of(TireDot tireDot, String nickname, Warehouse warehouse, Long quantity, boolean lock) {
        Stock stock = new Stock(tireDot, nickname);
        return stock.update(warehouse, quantity, lock);
    }

    public Stock update(Warehouse warehouse, Long quantity, boolean lock) {
        this.warehouse = warehouse;
        this.quantity = quantity;
        this.lock = lock;
        return this;
    }

    public Stock addQuantity(int quantity) {
        this.quantity += quantity;
        return this;
    }

    public Stock reduceQuantity(int quantity) {
        this.quantity -= quantity;
        return this;
    }
}
