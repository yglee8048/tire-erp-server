package com.minsoo.co.tireerpserver.account.service;

import com.minsoo.co.tireerpserver.api.error.exceptions.AlreadyExistException;
import com.minsoo.co.tireerpserver.api.error.exceptions.NotFoundException;
import com.minsoo.co.tireerpserver.model.dto.account.customer.CustomerRequest;
import com.minsoo.co.tireerpserver.account.entity.Customer;
import com.minsoo.co.tireerpserver.account.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer findById(Long id) {
        return customerRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public Customer create(CustomerRequest createRequest) {
        if (customerRepository.existsByUserId(createRequest.getUserId())) {
            throw new AlreadyExistException("이미 존재하는 아이디입니다.");
        }
        return customerRepository.save(Customer.of(createRequest));
    }

    @Transactional
    public void update(Long id, CustomerRequest updateRequest) {
        Customer customer = customerRepository.findById(id).orElseThrow(NotFoundException::new);
        if (!customer.getUserId().equals(updateRequest.getUserId()) && customerRepository.existsByUserId(updateRequest.getUserId())) {
            throw new AlreadyExistException("이미 존재하는 아이디입니다.");
        }
        customer.update(updateRequest);
    }

    @Transactional
    public void removeById(Long id) {
        customerRepository.deleteById(id);
    }
}
