package com.minsoo.co.tireerpserver.model.entity;

import com.minsoo.co.tireerpserver.model.dto.dot.memo.TireDotMemoCreateRequest;
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

    @Column(name = "lock", nullable = false)
    private boolean lock;

    //== Business ==//
    public TireDotMemo(TireDotMemoCreateRequest createRequest, TireDot tireDot) {
        this.tireDot = tireDot;
        this.memo = createRequest.getMemo();
        this.lock = createRequest.isLock();
    }

    public static TireDotMemo of(TireDotMemoCreateRequest createRequest, TireDot tireDot) {
        return new TireDotMemo(createRequest, tireDot);
    }

    public void updateMemo(String memo) {
        this.memo = memo;
    }

    public void updateLock(boolean lock) {
        this.lock = lock;
    }
}
