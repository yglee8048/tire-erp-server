package com.minsoo.co.tireerpserver.service.sale;

import com.minsoo.co.tireerpserver.entity.sale.Sale;
import com.minsoo.co.tireerpserver.repository.sale.SaleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SaleServiceTest {

    @Autowired
    SaleRepository saleRepository;

    @DisplayName("매출 생성 테스트")
    @Test
    void create() {

    }

    @Test
    void update() {
    }

    @Test
    void confirm() {
    }

    @Test
    void complete() {
    }

    @Test
    void deleteById() {
    }
}