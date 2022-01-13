package com.minsoo.co.tireerpserver.service.client;

import com.minsoo.co.tireerpserver.entity.client.ClientCompany;
import com.minsoo.co.tireerpserver.entity.rank.Rank;
import com.minsoo.co.tireerpserver.exception.NotFoundException;
import com.minsoo.co.tireerpserver.model.request.client.ClientCompanyRequest;
import com.minsoo.co.tireerpserver.model.response.client.ClientCompanyResponse;
import com.minsoo.co.tireerpserver.repository.client.ClientCompanyRepository;
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
public class ClientCompanyService {

    private final ClientCompanyRepository clientCompanyRepository;
    private final RankRepository rankRepository;

    public List<ClientCompanyResponse> findAll() {
        return clientCompanyRepository.findAllFetchRank().stream()
                .map(ClientCompanyResponse::new)
                .collect(Collectors.toList());
    }

    public ClientCompanyResponse findById(Long clientCompanyId) {
        return new ClientCompanyResponse(findClientCompanyById(clientCompanyId));
    }

    public ClientCompanyResponse create(ClientCompanyRequest clientCompanyRequest) {
        Rank rank = findRankById(clientCompanyRequest.getRankId());
        return new ClientCompanyResponse(clientCompanyRepository.save(ClientCompany.of(rank, clientCompanyRequest)));
    }

    public ClientCompanyResponse update(Long clientCompanyId, ClientCompanyRequest clientCompanyRequest) {
        ClientCompany clientCompany = findClientCompanyById(clientCompanyId);
        Rank rank = findRankById(clientCompanyRequest.getRankId());
        return new ClientCompanyResponse(clientCompany.update(rank, clientCompanyRequest));
    }

    public void deleteById(Long clientCompanyId) {
        ClientCompany clientCompany = findClientCompanyById(clientCompanyId);
        clientCompanyRepository.delete(clientCompany);
    }

    private Rank findRankById(Long rankId) {
        return rankRepository.findById(rankId).orElseThrow(() -> new NotFoundException("등급", rankId));
    }

    private ClientCompany findClientCompanyById(Long clientCompanyId) {
        return clientCompanyRepository.findFetchRankById(clientCompanyId).orElseThrow(() -> new NotFoundException("고객사", clientCompanyId));
    }
}
