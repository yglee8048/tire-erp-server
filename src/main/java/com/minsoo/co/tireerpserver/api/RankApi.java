package com.minsoo.co.tireerpserver.api;

import com.minsoo.co.tireerpserver.model.ApiResponse;
import com.minsoo.co.tireerpserver.model.request.rank.RankDotPriceRequest;
import com.minsoo.co.tireerpserver.model.request.rank.RankRequest;
import com.minsoo.co.tireerpserver.model.response.rank.RankDotPriceResponse;
import com.minsoo.co.tireerpserver.model.response.rank.RankResponse;
import com.minsoo.co.tireerpserver.service.rank.RankDotPriceService;
import com.minsoo.co.tireerpserver.service.rank.RankService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RankApi {

    private final RankService rankService;
    private final RankDotPriceService rankDotPriceService;

    @GetMapping("/ranks")
    public ApiResponse<List<RankResponse>> findAllRanks() {
        return ApiResponse.OK(rankService.findAll().stream()
                .map(RankResponse::new)
                .collect(Collectors.toList()));
    }

    @GetMapping("/ranks/{rankId}")
    public ApiResponse<RankResponse> findRankById(@PathVariable Long rankId) {
        return ApiResponse.OK(new RankResponse(rankService.findById(rankId)));
    }

    @PostMapping("/ranks")
    public ApiResponse<RankResponse> createRank(@RequestBody @Valid RankRequest rankRequest) {
        return ApiResponse.CREATED(new RankResponse(rankService.create(rankRequest)));
    }

    @PutMapping("/ranks/{rankId}")
    public ApiResponse<RankResponse> updateRank(@PathVariable Long rankId,
                                                @RequestBody @Valid RankRequest rankRequest) {
        return ApiResponse.CREATED(new RankResponse(rankService.update(rankId, rankRequest)));
    }

    @DeleteMapping("/ranks/{rankId}")
    public ApiResponse<Void> deleteRankById(@PathVariable Long rankId) {
        rankService.deleteById(rankId);
        return ApiResponse.NO_CONTENT();
    }

    @GetMapping("/tire-dots/{tireDotId}/rank-dot-prices")
    public ApiResponse<List<RankDotPriceResponse>> findRankDotPrices(@PathVariable Long tireDotId) {
        return ApiResponse.OK(rankDotPriceService.findAllByTireDotId(tireDotId).stream()
                .map(RankDotPriceResponse::new)
                .collect(Collectors.toList()));
    }

    @PutMapping("/tire-dots/{tireDotId}/ranks/{rankId}")
    public ApiResponse<RankDotPriceResponse> modifyRankDotPrice(@PathVariable Long tireDotId,
                                                                @PathVariable Long rankId,
                                                                @Valid @RequestBody RankDotPriceRequest rankDotPriceRequest) {
        return ApiResponse.OK(new RankDotPriceResponse(rankDotPriceService.modify(tireDotId, rankId, rankDotPriceRequest)));
    }
}
