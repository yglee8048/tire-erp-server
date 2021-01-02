package com.minsoo.co.tireerpserver.model.entity;

import com.minsoo.co.tireerpserver.model.entity.embedded.AdminHistory;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tire_dot")
public class TireDot {

    @Id
    @Column(name = "tire_dot_id", length = 20)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tire_id", nullable = false)
    private Tire tire;

    @Column(name = "dot", nullable = false)
    private String dot;

    @Column(name = "open", nullable = false)
    private boolean open;

    @Column(name = "price", nullable = false)
    private Integer price;

    @OneToMany(mappedBy = "tireDot", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TireDotMemo> tireDotMemos = new ArrayList<>();

    @Embedded
    private AdminHistory history;
}
