package com.minsoo.co.tireerpserver.service.account;

import com.minsoo.co.tireerpserver.constant.SystemMessage;
import com.minsoo.co.tireerpserver.entity.account.Client;
import com.minsoo.co.tireerpserver.entity.account.ClientCompany;
import com.minsoo.co.tireerpserver.exception.NotFoundException;
import com.minsoo.co.tireerpserver.model.account.request.ClientRequest;
import com.minsoo.co.tireerpserver.repository.account.ClientCompanyRepository;
import com.minsoo.co.tireerpserver.repository.account.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientCompanyRepository clientCompanyRepository;
    private final PasswordEncoder passwordEncoder;

    private ClientCompany findClientCompanyById(Long clientCompanyId) {
        return clientCompanyRepository.findById(clientCompanyId).orElseThrow(() -> {
            log.error("Can not find client company by id: {}", clientCompanyId);
            return new NotFoundException(SystemMessage.NOT_FOUND + ": [고객사]");
        });
    }

    public List<Client> findAllByClientCompany(Long clientCompanyId) {
        ClientCompany clientCompany = findClientCompanyById(clientCompanyId);
        return clientRepository.findAllByClientCompany(clientCompany);
    }

    public Client findById(Long clientId) {
        return clientRepository.findById(clientId).orElseThrow(() -> {
            log.error("Can not find client by id: {}", clientId);
            return new NotFoundException(SystemMessage.NOT_FOUND + ": [고객 계정]");
        });
    }

    public Client create(Long clientCompanyId, ClientRequest clientRequest) {
        ClientCompany clientCompany = findClientCompanyById(clientCompanyId);
        return clientRepository.save(Client.of(clientCompany, clientRequest, passwordEncoder));
    }

    public Client update(Long clientCompanyId, Long clientId, ClientRequest clientRequest) {
        ClientCompany clientCompany = findClientCompanyById(clientCompanyId);
        Client client = findById(clientId);
        return client.update(clientCompany, clientRequest, passwordEncoder);
    }

    public void deleteById(Long clientId) {
        Client client = findById(clientId);
        clientRepository.delete(client);
    }
}
