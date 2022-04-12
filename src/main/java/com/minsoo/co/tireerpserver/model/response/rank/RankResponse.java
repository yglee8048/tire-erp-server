package com.minsoo.co.tireerpserver.model.response.rank;

import com.minsoo.co.tireerpserver.entity.rank.Rank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RankResponse {

    private Long rankId;
    private String name;
    private String description;

    public RankResponse(Rank rank) {
        this.rankId = rank.getId();
        this.name = rank.getName();
        this.description = rank.getDescription();
    }
}
