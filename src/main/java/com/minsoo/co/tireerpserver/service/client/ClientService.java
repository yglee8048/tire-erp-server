package com.minsoo.co.tireerpserver.service.client;

import com.minsoo.co.tireerpserver.constant.SystemMessage;
import com.minsoo.co.tireerpserver.entity.client.Client;
import com.minsoo.co.tireerpserver.entity.client.ClientCompany;
import com.minsoo.co.tireerpserver.exception.BadRequestException;
import com.minsoo.co.tireerpserver.exception.NotFoundException;
import com.minsoo.co.tireerpserver.model.request.client.ClientRequest;
import com.minsoo.co.tireerpserver.model.response.client.ClientResponse;
import com.minsoo.co.tireerpserver.repository.account.AccountRepository;
import com.minsoo.co.tireerpserver.repository.client.ClientCompanyRepository;
import com.minsoo.co.tireerpserver.repository.client.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientCompanyRepository clientCompanyRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public List<ClientResponse> findAllByClientCompany(Long clientCompanyId) {
        ClientCompany clientCompany = findClientCompanyById(clientCompanyId);
        return clientRepository.findAllByClientCompany(clientCompany).stream()
                .map(ClientResponse::new)
                .collect(Collectors.toList());
    }

    public ClientResponse findById(Long clientId) {
        return new ClientResponse(findClientById(clientId));
    }

    public ClientResponse create(Long clientCompanyId, ClientRequest clientRequest) {
        if (accountRepository.existsByUsername(clientRequest.getUserId())) {
            throw new BadRequestException(SystemMessage.USERNAME_DUPLICATE);
        }
        ClientCompany clientCompany = findClientCompanyById(clientCompanyId);
        return new ClientResponse(clientRepository.save(Client.of(clientCompany, clientRequest, passwordEncoder)));
    }

    public ClientResponse update(Long clientId, ClientRequest clientRequest) {
        Client client = findClientById(clientId);
        if (!client.getUsername().equals(clientRequest.getUserId()) && accountRepository.existsByUsername(clientRequest.getUserId())) {
            throw new BadRequestException(SystemMessage.USERNAME_DUPLICATE);
        }
        return new ClientResponse(client.update(clientRequest, passwordEncoder));
    }

    public void deleteById(Long clientId) {
        Client client = findClientById(clientId);
        clientRepository.delete(client);
    }

    private Client findClientById(Long clientId) {
        return clientRepository.findById(clientId).orElseThrow(() -> new NotFoundException("고객 계정", clientId));
    }

    private ClientCompany findClientCompanyById(Long clientCompanyId) {
        return clientCompanyRepository.findById(clientCompanyId).orElseThrow(() -> new NotFoundException("고객사", clientCompanyId));
    }
}
