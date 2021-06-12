package com.minsoo.co.tireerpserver.sale.entity;

import com.minsoo.co.tireerpserver.sale.model.memo.SaleMemoRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "sale_memo")
public class SaleMemo {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "sale_memo_id", length = 20, nullable = false)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "sale_id", nullable = false)
    private Sale sale;

    @Column(name = "memo")
    private String memo;

    @Column(name = "is_lock")
    private Boolean lock;

    @Builder
    public SaleMemo(Sale sale, String memo, Boolean lock) {
        this.sale = sale;
        this.memo = memo;
        this.lock = lock;
    }

    public static SaleMemo of(Sale sale, SaleMemoRequest saleMemoRequest) {
        return SaleMemo.builder()
                .sale(sale)
                .memo(saleMemoRequest.getMemo())
                .lock(saleMemoRequest.isLock())
                .build();
    }

    public SaleMemo update(SaleMemoRequest saleMemoRequest) {
        this.memo = saleMemoRequest.getMemo();
        this.lock = saleMemoRequest.isLock();
        return this;
    }
}
