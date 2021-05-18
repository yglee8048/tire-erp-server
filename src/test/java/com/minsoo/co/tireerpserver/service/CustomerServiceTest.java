package com.minsoo.co.tireerpserver.service;

import com.minsoo.co.tireerpserver.api.error.exceptions.AlreadyExistException;
import com.minsoo.co.tireerpserver.model.dto.account.customer.CustomerRequest;
import com.minsoo.co.tireerpserver.model.dto.account.customer.CustomerResponse;
import com.minsoo.co.tireerpserver.service.account.CustomerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.minsoo.co.tireerpserver.utils.RequestBuilder.*;
import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceTest extends ServiceTest {

    @Autowired
    CustomerService customerService;

    /**
     * 1. 거래처의 userId는 중복될 수 없다.
     */
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