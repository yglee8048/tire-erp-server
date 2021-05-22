package com.minsoo.co.tireerpserver.model.entity.entities.tire;

import com.minsoo.co.tireerpserver.model.code.TireOption;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "tire_options")
public class TireOptions {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "tire_options_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "tire_id", nullable = false)
    private Tire tire;

    @Enumerated(STRING)
    @Column(name = "option")
    private TireOption option;
}
