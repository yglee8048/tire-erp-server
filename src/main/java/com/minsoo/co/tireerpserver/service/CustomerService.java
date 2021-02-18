package com.minsoo.co.tireerpserver.service;

import com.minsoo.co.tireerpserver.api.error.errors.AlreadyExistException;
import com.minsoo.co.tireerpserver.api.error.errors.NotFoundException;
import com.minsoo.co.tireerpserver.model.dto.customer.CustomerRequest;
import com.minsoo.co.tireerpserver.model.entity.Customer;
import com.minsoo.co.tireerpserver.repository.CustomerRepository;
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
    public Customer update(Long id, CustomerRequest updateRequest) {
        Customer customer = this.findById(id);
        if (!customer.getUserId().equals(updateRequest.getUserId()) && customerRepository.existsByUserId(updateRequest.getUserId())) {
            throw new AlreadyExistException("이미 존재하는 아이디입니다.");
        }
        customer.update(updateRequest);
        return customer;
    }

    @Transactional
    public void removeById(Long id) {
        customerRepository.deleteById(id);
    }
}
