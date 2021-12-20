package com.minsoo.co.tireerpserver.service.rank;

import com.minsoo.co.tireerpserver.constant.SystemMessage;
import com.minsoo.co.tireerpserver.entity.rank.Rank;
import com.minsoo.co.tireerpserver.exception.NotFoundException;
import com.minsoo.co.tireerpserver.model.request.rank.RankRequest;
import com.minsoo.co.tireerpserver.repository.rank.RankRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RankService {

    private final RankRepository rankRepository;

    public List<Rank> findAll() {
        return rankRepository.findAll();
    }

    public Rank findById(Long rankId) {
        return rankRepository.findById(rankId).orElseThrow(() -> {
            log.error("Can not find rank by id: {}", rankId);
            return new NotFoundException(SystemMessage.NOT_FOUND + ": [등급]");
        });
    }

    public Rank create(RankRequest rankRequest) {
        return rankRepository.save(Rank.of(rankRequest));
    }

    public Rank update(Long rankId, RankRequest rankRequest) {
        Rank rank = findById(rankId);
        return rank.update(rankRequest);
    }

    public void deleteById(Long rankId) {
        Rank rank = findById(rankId);
        rankRepository.delete(rank);
    }
}
