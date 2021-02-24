package com.minsoo.co.tireerpserver.service;

import com.minsoo.co.tireerpserver.api.error.errors.AlreadyExistException;
import com.minsoo.co.tireerpserver.model.dto.customer.CustomerRequest;
import com.minsoo.co.tireerpserver.model.dto.customer.CustomerResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.minsoo.co.tireerpserver.utils.RequestBuilder.*;
import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceTest extends ServiceTest {

    @Autowired
    CustomerService customerService;

    @Test
    @DisplayName("거래처 생성 테스트")
    void createCustomer() {
        CustomerRequest request = CUSTOMER("test_id");
        CustomerResponse response = customerService.create(request);
        assertEquals("test_id", response.getUserId());

        CustomerRequest duplicate = CUSTOMER("test_id");
        assertThrows(AlreadyExistException.class, () -> customerService.create(duplicate));
    }
}