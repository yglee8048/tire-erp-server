package com.minsoo.co.tireerpserver.service.client;

import com.minsoo.co.tireerpserver.constant.SystemMessage;
import com.minsoo.co.tireerpserver.entity.client.ClientCompany;
import com.minsoo.co.tireerpserver.exception.NotFoundException;
import com.minsoo.co.tireerpserver.model.request.client.ClientCompanyRequest;
import com.minsoo.co.tireerpserver.repository.client.ClientCompanyRepository;
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

    public List<ClientCompany> findAll() {
        return clientCompanyRepository.findAll();
    }

    public ClientCompany findById(Long clientCompanyId) {
        return clientCompanyRepository.findById(clientCompanyId).orElseThrow(() -> {
            log.error("Can not find client company by id: {}", clientCompanyId);
            return new NotFoundException(SystemMessage.NOT_FOUND + ": [고객사]");
        });
    }

    public ClientCompany create(ClientCompanyRequest clientCompanyRequest) {
        return clientCompanyRepository.save(ClientCompany.of(clientCompanyRequest));
    }

    public ClientCompany update(Long clientCompanyId, ClientCompanyRequest clientCompanyRequest) {
        ClientCompany clientCompany = findById(clientCompanyId);
        return clientCompany.update(clientCompanyRequest);
    }

    public void deleteById(Long clientCompanyId) {
        ClientCompany clientCompany = findById(clientCompanyId);
        clientCompanyRepository.delete(clientCompany);
    }
}
