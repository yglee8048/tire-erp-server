package com.minsoo.co.tireerpserver.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "tire_dot")
public class TireDot {

    @Id
    @Column(name = "tire_dot_id", length = 20)
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "tire_id", nullable = false)
    private Tire tire;

    @Column(name = "dot", nullable = false)
    private String dot;

    @Column(name = "open", nullable = false)
    private boolean open;

    @Column(name = "price", nullable = false)
    private Integer price;

    @OneToMany(mappedBy = "tireDot", fetch = LAZY, cascade = ALL, orphanRemoval = true)
    private List<TireDotMemo> tireDotMemos = new ArrayList<>();
}
