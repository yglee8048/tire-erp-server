package com.minsoo.co.tireerpserver.service;

import com.minsoo.co.tireerpserver.api.error.exceptions.AlreadyExistException;
import com.minsoo.co.tireerpserver.api.error.exceptions.NotFoundException;
import com.minsoo.co.tireerpserver.model.dto.customer.CustomerRequest;
import com.minsoo.co.tireerpserver.model.dto.customer.CustomerResponse;
import com.minsoo.co.tireerpserver.model.entity.Customer;
import com.minsoo.co.tireerpserver.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public List<CustomerResponse> findAll() {
        return customerRepository.findAll()
                .stream()
                .map(CustomerResponse::of)
                .collect(Collectors.toList());
    }

    public CustomerResponse findById(Long id) {
        return CustomerResponse.of(customerRepository.findById(id).orElseThrow(NotFoundException::new));
    }

    @Transactional
    public CustomerResponse create(CustomerRequest createRequest) {
        if (customerRepository.existsByUserId(createRequest.getUserId())) {
            throw new AlreadyExistException("이미 존재하는 아이디입니다.");
        }
        return CustomerResponse.of(customerRepository.save(Customer.of(createRequest)));
    }

    @Transactional
    public CustomerResponse update(Long id, CustomerRequest updateRequest) {
        Customer customer = customerRepository.findById(id).orElseThrow(NotFoundException::new);
        if (!customer.getUserId().equals(updateRequest.getUserId()) && customerRepository.existsByUserId(updateRequest.getUserId())) {
            throw new AlreadyExistException("이미 존재하는 아이디입니다.");
        }
        customer.update(updateRequest);
        return CustomerResponse.of(customer);
    }

    @Transactional
    public void removeById(Long id) {
        customerRepository.deleteById(id);
    }
}
