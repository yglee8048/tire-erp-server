package com.minsoo.co.tireerpserver.model.entity;

import com.minsoo.co.tireerpserver.model.entity.audit.BaseEntity;
import com.minsoo.co.tireerpserver.model.entity.embedded.AdminHistory;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tire_dot_memo")
public class TireDotMemo extends BaseEntity {

    @Id
    @Column(name = "tire_dot_memo_id", length = 20)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tire_dot_id")
    private TireDot tireDot;

    @Column(name = "memo")
    private String memo;

    @Column(name = "open", nullable = false)
    private boolean open;

    @Embedded
    private AdminHistory history;
}
