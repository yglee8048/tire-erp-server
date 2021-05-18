package com.minsoo.co.tireerpserver.model.entity.entities.tire;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "tire_dot",
        uniqueConstraints = {@UniqueConstraint(name = "tire_dot_unique_dot", columnNames = {"tire_id", "dot"})})
public class TireDot {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "tire_dot_id", length = 20)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "tire_id", nullable = false)
    private Tire tire;

    @Column(name = "dot", nullable = false)
    private String dot;

    private TireDot(Tire tire, String dot) {
        this.tire = tire;
        this.dot = dot;
    }

    public static TireDot of(Tire tire, String dot) {
        return new TireDot(tire, dot);
    }
}
