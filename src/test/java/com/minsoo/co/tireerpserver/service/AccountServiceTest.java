package com.minsoo.co.tireerpserver.service;

import com.minsoo.co.tireerpserver.service.account.AccountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class AccountServiceTest extends ServiceTest {

    @Autowired
    AccountService accountService;

    @Test
    @DisplayName("account 생성 테스트")
    public void createTest(){

    }
}