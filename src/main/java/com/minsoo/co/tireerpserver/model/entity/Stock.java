package com.minsoo.co.tireerpserver.model.entity;

import com.minsoo.co.tireerpserver.model.entity.audit.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "stock")
public class Stock extends BaseEntity {

    @Id
    @Column(name = "warehouse_id", length = 20)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tire_dot_id", nullable = false)
    private TireDot tireDot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @Column(name = "quantity", nullable = false)
    private Long quantity;
}
