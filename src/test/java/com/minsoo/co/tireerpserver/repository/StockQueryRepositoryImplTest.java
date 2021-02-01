package com.minsoo.co.tireerpserver.repository;

import com.minsoo.co.tireerpserver.repository.query.StockQueryRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StockQueryRepositoryImplTest {

    @Autowired
    StockQueryRepositoryImpl stockQueryRepositoryImpl;

    @Test
    public void testQuery() {
        stockQueryRepositoryImpl.findTireStocks(null, null, null, null);
    }
}