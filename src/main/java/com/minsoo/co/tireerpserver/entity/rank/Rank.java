package com.minsoo.co.tireerpserver.entity.rank;

import com.minsoo.co.tireerpserver.entity.BaseEntity;
import com.minsoo.co.tireerpserver.model.request.rank.RankRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "rank")
public class Rank extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rank_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    public static Rank of(RankRequest rankRequest) {
        Rank rank = new Rank();
        return rank.update(rankRequest);
    }

    public Rank update(RankRequest rankRequest) {
        this.name = rankRequest.getName();
        this.description = rankRequest.getDescription();
        return this;
    }
}
