package com.minsoo.co.tireerpserver.api;

import com.minsoo.co.tireerpserver.model.ApiResponse;
import com.minsoo.co.tireerpserver.model.request.rank.RankDotPriceRequest;
import com.minsoo.co.tireerpserver.model.request.rank.RankRequest;
import com.minsoo.co.tireerpserver.model.response.rank.RankDotPriceGridResponse;
import com.minsoo.co.tireerpserver.model.response.rank.RankResponse;
import com.minsoo.co.tireerpserver.service.rank.RankDotPriceService;
import com.minsoo.co.tireerpserver.service.rank.RankService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RankApi {

    private final RankService rankService;
    private final RankDotPriceService rankDotPriceService;

    @GetMapping("/ranks")
    public ApiResponse<List<RankResponse>> findAllRanks() {
        return ApiResponse.OK(rankService.findAll());
    }

    @GetMapping("/ranks/{rankId}")
    public ApiResponse<RankResponse> findRankById(@PathVariable Long rankId) {
        return ApiResponse.OK(rankService.findById(rankId));
    }

    @PostMapping("/ranks")
    public ApiResponse<RankResponse> createRank(@RequestBody @Valid RankRequest rankRequest) {
        return ApiResponse.CREATED(rankService.create(rankRequest));
    }

    @PutMapping("/ranks/{rankId}")
    public ApiResponse<RankResponse> updateRank(@PathVariable Long rankId,
                                                @RequestBody @Valid RankRequest rankRequest) {
        return ApiResponse.CREATED(rankService.update(rankId, rankRequest));
    }

    @DeleteMapping("/ranks/{rankId}")
    public ApiResponse<Void> deleteRankById(@PathVariable Long rankId) {
        rankService.deleteById(rankId);
        return ApiResponse.NO_CONTENT();
    }

    @GetMapping("/tire-dots/{tireDotId}/rank-dot-prices")
    public ApiResponse<List<RankDotPriceGridResponse>> findRankDotPrices(@PathVariable Long tireDotId) {
        return ApiResponse.OK(rankDotPriceService.findAllByTireDotId(tireDotId));
    }

    @PutMapping("/tire-dots/{tireDotId}/ranks")
    public ApiResponse<Void> modifyRankDotPrice(@PathVariable Long tireDotId,
                                                @Valid @RequestBody List<RankDotPriceRequest> rankDotPriceRequests) {
        rankDotPriceService.modify(tireDotId, rankDotPriceRequests);
        return ApiResponse.NO_CONTENT();
    }
}
