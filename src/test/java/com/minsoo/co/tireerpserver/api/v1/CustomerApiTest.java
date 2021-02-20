package com.minsoo.co.tireerpserver.api.v1;

import com.minsoo.co.tireerpserver.api.ApiTestSupport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

class CustomerApiTest extends ApiTestSupport {

    @Autowired
    private CustomerApi customerApi;

    @Override
    protected Object controller() {
        return customerApi;
    }

    @Test
    void findAllCustomers() throws Exception {
        mockMvc.perform(get("/api/v1/customers"));
    }
}