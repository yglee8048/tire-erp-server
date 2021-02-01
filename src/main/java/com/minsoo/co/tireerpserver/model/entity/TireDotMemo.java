package com.minsoo.co.tireerpserver.model.entity;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "tire_dot_memo")
public class TireDotMemo {

    @Id
    @Column(name = "tire_dot_memo_id", length = 20)
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "tire_dot_id")
    private TireDot tireDot;

    @Column(name = "memo")
    private String memo;

    @Column(name = "open", nullable = false)
    private boolean open;
}
