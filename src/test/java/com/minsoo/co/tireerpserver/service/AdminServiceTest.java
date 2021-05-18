package com.minsoo.co.tireerpserver.service;

import com.minsoo.co.tireerpserver.service.account.AdminService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class AdminServiceTest extends ServiceTest {

    @Autowired
    AdminService adminService;

    /**
     * 1. Admin 의 아이디는 중복될 수 없다.
     */
    @Test
    @DisplayName("관리자 생성 테스트")
    public void createAdmin() {

    }
}