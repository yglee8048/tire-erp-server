package com.minsoo.co.tireerpserver.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Transactional
@SpringBootTest
public abstract class ServiceTest {

    protected static final Logger log = LoggerFactory.getLogger(ServiceTest.class);

    @Autowired
    private EntityManager em;

    protected void clear() {
        em.flush();
        em.clear();
    }
}
