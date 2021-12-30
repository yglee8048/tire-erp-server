package com.minsoo.co.tireerpserver.model.response.rank;

import com.minsoo.co.tireerpserver.entity.rank.RankDotPrice;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RankDotPriceResponse {

    private Long rankId;
    private String rankName;
    private Long tireDotId;
    private String dot;
    private Long price;

    public RankDotPriceResponse(RankDotPrice rankDotPrice) {
        this.rankId = rankDotPrice.getRank().getId();
        this.rankName = rankDotPrice.getRank().getName();
        this.tireDotId = rankDotPrice.getTireDot().getId();
        this.dot = rankDotPrice.getTireDot().getDot();
        this.price = rankDotPrice.getPrice();
    }
}
