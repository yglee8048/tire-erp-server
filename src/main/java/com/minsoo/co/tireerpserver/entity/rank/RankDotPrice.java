package com.minsoo.co.tireerpserver.entity.rank;

import com.minsoo.co.tireerpserver.entity.BaseEntity;
import com.minsoo.co.tireerpserver.entity.tire.TireDot;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "rank_dot_price")
public class RankDotPrice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rank_dot_price_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rank_id", referencedColumnName = "rank_id")
    private Rank rank;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tire_dot_id", referencedColumnName = "tire_dot_id")
    private TireDot tireDot;

    @Column(name = "price")
    private Long price;

    private RankDotPrice(Rank rank, TireDot tireDot) {
        this.rank = rank;
        this.tireDot = tireDot;
    }

    public static RankDotPrice of(Rank rank, TireDot tireDot, Long price) {
        RankDotPrice rankDotPrice = new RankDotPrice(rank, tireDot);
        return rankDotPrice.update(price);
    }

    public RankDotPrice update(Long price) {
        this.price = price;
        return this;
    }
}
