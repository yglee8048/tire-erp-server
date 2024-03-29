package com.minsoo.co.tireerpserver.repository.rank;

import com.minsoo.co.tireerpserver.entity.rank.Rank;
import com.minsoo.co.tireerpserver.entity.rank.RankDotPrice;
import com.minsoo.co.tireerpserver.entity.tire.TireDot;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RankDotPriceRepository extends JpaRepository<RankDotPrice, Long>, RankDotPriceQueryRepository {

    @EntityGraph(attributePaths = {"rank", "tireDot"})
    List<RankDotPrice> findAllByTireDot(TireDot tireDot);

    @EntityGraph(attributePaths = {"rank", "tireDot"})
    Optional<RankDotPrice> findByRankAndTireDot(Rank rank, TireDot tireDot);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("delete from RankDotPrice rdp where rdp.id in :id")
    void deleteAllById(List<Long> id);
}
