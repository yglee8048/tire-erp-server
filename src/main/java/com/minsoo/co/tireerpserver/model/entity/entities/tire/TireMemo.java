package com.minsoo.co.tireerpserver.model.entity.entities.tire;

import com.minsoo.co.tireerpserver.model.dto.tire.memo.TireMemoRequest;
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
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "tire_memo_id", length = 20)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "tire_id", nullable = false)
    private Tire tire;

    @Column(name = "memo")
    private String memo;

    @Column(name = "is_lock", nullable = false)
    private Boolean lock;

    //== Business ==//
    private TireMemo(TireMemoRequest createRequest, Tire tire) {
        this.tire = tire;
        this.memo = createRequest.getMemo();
        this.lock = createRequest.isLock();
    }

    public static TireMemo of(TireMemoRequest createRequest, Tire tire) {
        return new TireMemo(createRequest, tire);
    }

    public TireMemo update(TireMemoRequest updateRequest) {
        this.memo = updateRequest.getMemo();
        this.lock = updateRequest.isLock();

        return this;
    }
}
