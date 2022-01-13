package com.minsoo.co.tireerpserver.service.tire;

import com.minsoo.co.tireerpserver.entity.client.ClientCompany;
import com.minsoo.co.tireerpserver.exception.NotFoundException;
import com.minsoo.co.tireerpserver.model.response.tire.TireDotGridResponse;
import com.minsoo.co.tireerpserver.repository.client.ClientCompanyRepository;
import com.minsoo.co.tireerpserver.repository.tire.TireDotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TireDotService {

    private final TireDotRepository tireDotRepository;
    private final ClientCompanyRepository clientCompanyRepository;

    public List<TireDotGridResponse> findTireDotGridsByTireIdAndOptionalClientCompanyId(Long tireId, Long clientCompanyId) {
        Long rankId = null;
        if (clientCompanyId != null) {
            ClientCompany clientCompany = clientCompanyRepository.findById(clientCompanyId).orElseThrow(() -> new NotFoundException("고객사", clientCompanyId));
            rankId = clientCompany.getRank().getId();
        }
        return tireDotRepository.findTireDotGridsByTireIdAndOptionalRankId(tireId, rankId);
    }
}
