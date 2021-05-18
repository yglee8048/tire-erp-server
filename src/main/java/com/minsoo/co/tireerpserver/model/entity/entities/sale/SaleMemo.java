package com.minsoo.co.tireerpserver.model.entity.entities.sale;

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
    private boolean lock;
}
