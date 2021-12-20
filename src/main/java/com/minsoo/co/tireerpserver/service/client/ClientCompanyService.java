package com.minsoo.co.tireerpserver.service.client;

import com.minsoo.co.tireerpserver.constant.SystemMessage;
import com.minsoo.co.tireerpserver.entity.client.ClientCompany;
import com.minsoo.co.tireerpserver.entity.rank.Rank;
import com.minsoo.co.tireerpserver.exception.NotFoundException;
import com.minsoo.co.tireerpserver.model.request.client.ClientCompanyRequest;
import com.minsoo.co.tireerpserver.repository.client.ClientCompanyRepository;
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
public class ClientCompanyService {

    private final ClientCompanyRepository clientCompanyRepository;
    private final RankRepository rankRepository;

    public List<ClientCompany> findAll() {
        return clientCompanyRepository.findAllFetchRank();
    }

    public ClientCompany findById(Long clientCompanyId) {
        return clientCompanyRepository.findFetchRankById(clientCompanyId).orElseThrow(() -> {
            log.error("Can not find client company by id: {}", clientCompanyId);
            return new NotFoundException(SystemMessage.NOT_FOUND + ": [고객사]");
        });
    }

    public ClientCompany create(ClientCompanyRequest clientCompanyRequest) {
        Rank rank = findRankById(clientCompanyRequest.getRankId());
        return clientCompanyRepository.save(ClientCompany.of(rank, clientCompanyRequest));
    }

    public ClientCompany update(Long clientCompanyId, ClientCompanyRequest clientCompanyRequest) {
        ClientCompany clientCompany = findById(clientCompanyId);
        Rank rank = findRankById(clientCompanyRequest.getRankId());
        return clientCompany.update(rank, clientCompanyRequest);
    }

    public void deleteById(Long clientCompanyId) {
        ClientCompany clientCompany = findById(clientCompanyId);
        clientCompanyRepository.delete(clientCompany);
    }

    private Rank findRankById(Long rankId) {
        return rankRepository.findById(rankId).orElseThrow(() -> {
            log.error("Can not find rank by id: {}", rankId);
            return new NotFoundException(SystemMessage.NOT_FOUND + ": [등급]");
        });
    }
}
