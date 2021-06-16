package com.minsoo.co.tireerpserver.user.service;

import com.minsoo.co.tireerpserver.shared.error.exceptions.NotFoundException;
import com.minsoo.co.tireerpserver.user.entity.Admin;
import com.minsoo.co.tireerpserver.user.model.admin.AdminRequest;
import com.minsoo.co.tireerpserver.user.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final AccountService accountService;

    public List<Admin> findAll() {
        return adminRepository.findAll();
    }

    public Admin findById(Long id) {
        return adminRepository.findById(id).orElseThrow(() -> NotFoundException.of("관리자"));
    }

    public Admin create(AdminRequest adminRequest) {
        return adminRepository.save(Admin.of(adminRequest, accountService));
    }
}
