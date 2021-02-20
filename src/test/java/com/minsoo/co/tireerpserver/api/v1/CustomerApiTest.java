package com.minsoo.co.tireerpserver.api.v1;

import com.minsoo.co.tireerpserver.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerApiTest {

    private MockMvc mvc;

    @MockBean
    private CustomerService customerService;

    @Test
    void findAllCustomers() {
    }

    @Test
    void findCustomerById() {
    }

    @Test
    void createCustomer() {
    }

    @Test
    void updateCustomer() {
    }

    @Test
    void deleteCustomer() {
    }
}