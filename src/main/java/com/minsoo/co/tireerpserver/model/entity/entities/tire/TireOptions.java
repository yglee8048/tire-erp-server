package com.minsoo.co.tireerpserver.model.entity.entities.tire;

import com.minsoo.co.tireerpserver.model.code.TireOption;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "tire_options")
public class TireOptions {

    @Id
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "tire_id", nullable = false)
    private Tire tire;

    @Enumerated(STRING)
    @Column(name = "option", columnDefinition = "옵션 정보(런플렛, 스펀지, 실링)")
    private TireOption option;
}
