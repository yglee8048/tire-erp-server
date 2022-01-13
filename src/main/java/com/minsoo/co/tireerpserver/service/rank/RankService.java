package com.minsoo.co.tireerpserver.service.rank;

import com.minsoo.co.tireerpserver.entity.rank.Rank;
import com.minsoo.co.tireerpserver.exception.NotFoundException;
import com.minsoo.co.tireerpserver.model.request.rank.RankRequest;
import com.minsoo.co.tireerpserver.model.response.rank.RankResponse;
import com.minsoo.co.tireerpserver.repository.rank.RankRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RankService {

    private final RankRepository rankRepository;

    public List<RankResponse> findAll() {
        return rankRepository.findAll().stream()
                .map(RankResponse::new)
                .collect(Collectors.toList());
    }

    public RankResponse findById(Long rankId) {
        return new RankResponse(findRankById(rankId));
    }

    public RankResponse create(RankRequest rankRequest) {
        return new RankResponse(rankRepository.save(Rank.of(rankRequest)));
    }

    public RankResponse update(Long rankId, RankRequest rankRequest) {
        Rank rank = findRankById(rankId);
        return new RankResponse(rank.update(rankRequest));
    }

    public void deleteById(Long rankId) {
        Rank rank = findRankById(rankId);
        rankRepository.delete(rank);
    }

    private Rank findRankById(Long rankId) {
        return rankRepository.findById(rankId).orElseThrow(() -> new NotFoundException("고객사 등급", rankId));
    }
}
