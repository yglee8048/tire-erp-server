package com.minsoo.co.tireerpserver.user.service;

import com.minsoo.co.tireerpserver.user.entity.Customer;
import com.minsoo.co.tireerpserver.user.repository.CustomerRepository;
import com.minsoo.co.tireerpserver.shared.error.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer findById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new NotFoundException("고객", id));
    }
}
