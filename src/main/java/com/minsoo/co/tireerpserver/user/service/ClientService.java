package com.minsoo.co.tireerpserver.user.service;

import com.minsoo.co.tireerpserver.shared.error.exceptions.AlreadyExistException;
import com.minsoo.co.tireerpserver.user.entity.Client;
import com.minsoo.co.tireerpserver.user.entity.ClientCompany;
import com.minsoo.co.tireerpserver.user.model.client.ClientRequest;
import com.minsoo.co.tireerpserver.user.repository.AccountRepository;
import com.minsoo.co.tireerpserver.user.repository.ClientCompanyRepository;
import com.minsoo.co.tireerpserver.user.repository.ClientRepository;
import com.minsoo.co.tireerpserver.shared.error.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientCompanyRepository clientCompanyRepository;
    private final AccountRepository accountRepository;
    private final AccountService accountService;

    public List<Client> findAllByClientCompanyId(Long clientCompanyId) {
        ClientCompany clientCompany = clientCompanyRepository.findById(clientCompanyId).orElseThrow(() -> NotFoundException.of("고객사"));
        return clientRepository.findAllByClientCompany(clientCompany);
    }

    public Client findById(Long id) {
        return clientRepository.findById(id).orElseThrow(() -> NotFoundException.of("고객"));
    }

    @Transactional
    public Client create(Long clientCompanyId, ClientRequest clientRequest) {
        ClientCompany clientCompany = clientCompanyRepository.findById(clientCompanyId).orElseThrow(() -> NotFoundException.of("고객사"));
        if (accountRepository.existsByUsername(clientRequest.getUserId())) {
            throw new AlreadyExistException("이미 존재하는 계정 아이디입니다.");
        }
        return clientRepository.save(Client.of(clientRequest, clientCompany, accountService));
    }

    @Transactional
    public Client update(Long clientCompanyId, Long clientId, ClientRequest clientRequest) {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> NotFoundException.of("고객"));
        ClientCompany clientCompany = clientCompanyRepository.findById(clientCompanyId).orElseThrow(() -> NotFoundException.of("고객사"));
        if (!client.getUsername().equals(clientRequest.getUserId()) && accountRepository.existsByUsername(clientRequest.getUserId())) {
            throw new AlreadyExistException("이미 존재하는 계정 아이디입니다.");
        }
        return client.update(clientRequest, clientCompany);
    }

    @Transactional
    public void removeById(Long clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> NotFoundException.of("고객"));
        clientRepository.delete(client);
    }
}
