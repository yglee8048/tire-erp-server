package com.minsoo.co.tireerpserver.user.service;

import com.minsoo.co.tireerpserver.shared.error.exceptions.NotFoundException;
import com.minsoo.co.tireerpserver.user.entity.ClientCompany;
import com.minsoo.co.tireerpserver.user.model.client.company.ClientCompanyRequest;
import com.minsoo.co.tireerpserver.user.repository.ClientCompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClientCompanyService {

    private final ClientCompanyRepository clientCompanyRepository;

    public List<ClientCompany> findAll() {
        return clientCompanyRepository.findAll();
    }

    public ClientCompany findById(Long id) {
        return clientCompanyRepository.findById(id).orElseThrow(() -> NotFoundException.of("고객사"));
    }

    public ClientCompany create(ClientCompanyRequest clientCompanyRequest) {
        return clientCompanyRepository.save(ClientCompany.of(clientCompanyRequest));
    }

    public ClientCompany update(Long id, ClientCompanyRequest clientCompanyRequest) {
        ClientCompany clientCompany = clientCompanyRepository.findById(id).orElseThrow(() -> NotFoundException.of("고객사"));
        return clientCompany.update(clientCompanyRequest);
    }

    public void removeById(Long id) {
        ClientCompany clientCompany = clientCompanyRepository.findById(id).orElseThrow(() -> NotFoundException.of("고객사"));
        clientCompanyRepository.delete(clientCompany);
    }
}
