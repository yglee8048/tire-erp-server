package com.minsoo.co.tireerpserver.entity.sale;

import com.minsoo.co.tireerpserver.entity.BaseEntity;
import com.minsoo.co.tireerpserver.model.request.customer.sale.CustomerSaleMemoRequest;
import com.minsoo.co.tireerpserver.model.request.sale.SaleMemoRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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

    @Column(name = "is_admin")
    private Boolean admin;

    @Column(name = "is_lock")
    private Boolean lock;

    public SaleMemo(Sale sale, Boolean admin) {
        this.sale = sale;
        this.admin = admin;
        sale.getSaleMemos().add(this);
    }

    public static SaleMemo of(Sale sale, SaleMemoRequest saleMemoRequest) {
        SaleMemo saleMemo = new SaleMemo(sale, true);
        return saleMemo.update(saleMemoRequest.getMemo(), saleMemoRequest.isLock());
    }

    public static SaleMemo of(Sale sale, CustomerSaleMemoRequest customerSaleMemoRequest) {
        SaleMemo saleMemo = new SaleMemo(sale, false);
        return saleMemo.update(customerSaleMemoRequest.getMemo(), false);
    }

    public SaleMemo update(String memo, Boolean lock) {
        this.memo = memo;
        this.lock = lock;
        return this;
    }
}
