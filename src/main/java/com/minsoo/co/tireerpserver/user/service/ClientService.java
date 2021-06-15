package com.minsoo.co.tireerpserver.user.service;

import com.minsoo.co.tireerpserver.user.entity.Client;
import com.minsoo.co.tireerpserver.user.repository.ClientRepository;
import com.minsoo.co.tireerpserver.shared.error.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Client findById(Long id) {
        return clientRepository.findById(id).orElseThrow(() -> NotFoundException.of("고객"));
    }
}
