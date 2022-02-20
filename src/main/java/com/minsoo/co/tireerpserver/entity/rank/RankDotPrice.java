package com.minsoo.co.tireerpserver.entity.rank;

import com.minsoo.co.tireerpserver.entity.BaseEntity;
import com.minsoo.co.tireerpserver.entity.tire.TireDot;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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

    @Column(name = "discount_rate")
    private Float discountRate;

    private RankDotPrice(Rank rank, TireDot tireDot) {
        this.rank = rank;
        this.tireDot = tireDot;
    }

    public static RankDotPrice of(Rank rank, TireDot tireDot, Float discountRate) {
        RankDotPrice rankDotPrice = new RankDotPrice(rank, tireDot);
        return rankDotPrice.updateDiscountPrice(discountRate);
    }

    public RankDotPrice updateDiscountPrice(Float discountRate) {
        this.discountRate = discountRate;
        return this;
    }
}
