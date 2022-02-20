package com.minsoo.co.tireerpserver.entity.tire;

import com.minsoo.co.tireerpserver.entity.BaseEntity;
import com.minsoo.co.tireerpserver.model.request.tire.TireMemoRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tire_memo")
public class TireMemo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tire_memo_id", length = 20)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tire_id", nullable = false)
    private Tire tire;

    @Column(name = "memo")
    private String memo;

    @Column(name = "is_lock", nullable = false)
    private Boolean lock;

    public static TireMemo of(Tire tire, TireMemoRequest tireMemoRequest) {
        TireMemo tireMemo = new TireMemo();
        return tireMemo.update(tire, tireMemoRequest);
    }

    public TireMemo update(Tire tire, TireMemoRequest tireMemoRequest) {
        this.tire = tire;
        this.memo = tireMemoRequest.getMemo();
        this.lock = tireMemoRequest.isLock();
        return this;
    }
}
