package com.minsoo.co.tireerpserver.entity.sale;

import com.minsoo.co.tireerpserver.entity.BaseEntity;
import com.minsoo.co.tireerpserver.model.request.sale.SaleMemoRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "sale_memo")
public class SaleMemo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_memo_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_id", referencedColumnName = "sale_id")
    private Sale sale;

    @Column(name = "memo")
    private String memo;

    @Column(name = "is_lock")
    private Boolean lock;

    public SaleMemo(Sale sale) {
        this.sale = sale;
    }

    public static SaleMemo of(Sale sale, SaleMemoRequest saleMemoRequest) {
        SaleMemo saleMemo = new SaleMemo(sale);
        return saleMemo.update(saleMemoRequest);
    }

    public SaleMemo update(SaleMemoRequest saleMemoRequest) {
        this.memo = saleMemoRequest.getMemo();
        this.lock = saleMemoRequest.isLock();
        return this;
    }
}
