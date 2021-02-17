package com.minsoo.co.tireerpserver.model.entity;

import com.minsoo.co.tireerpserver.model.dto.dot.memo.TireMemoCreateRequest;
import com.minsoo.co.tireerpserver.model.dto.dot.memo.TireMemoUpdateRequest;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "tire_memo")
public class TireMemo {

    @Id
    @Column(name = "tire_memo_id", length = 20)
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "tire_id", nullable = false)
    private Tire tire;

    @Column(name = "memo")
    private String memo;

    @Column(name = "lock", nullable = false)
    private boolean lock;

    //== Business ==//
    public TireMemo(TireMemoCreateRequest createRequest, Tire tire) {
        this.tire = tire;
        this.memo = createRequest.getMemo();
        this.lock = createRequest.isLock();
    }

    public static TireMemo of(TireMemoCreateRequest createRequest, Tire tire) {
        return new TireMemo(createRequest, tire);
    }

    public void update(TireMemoUpdateRequest updateRequest) {
        this.memo = updateRequest.getMemo();
        this.lock = updateRequest.isLock();
    }
}
