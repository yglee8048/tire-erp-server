package com.minsoo.co.tireerpserver.user.service;

import com.minsoo.co.tireerpserver.shared.error.exceptions.AlreadyExistException;
import com.minsoo.co.tireerpserver.shared.error.exceptions.NotFoundException;
import com.minsoo.co.tireerpserver.user.entity.Admin;
import com.minsoo.co.tireerpserver.user.model.admin.AdminRequest;
import com.minsoo.co.tireerpserver.user.repository.AccountRepository;
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
    private final AccountRepository accountRepository;
    private final AccountService accountService;

    public List<Admin> findAll() {
        return adminRepository.findAll();
    }

    public Admin findById(Long id) {
        return adminRepository.findById(id).orElseThrow(() -> NotFoundException.of("관리자"));
    }

    public Admin create(AdminRequest adminRequest) {
        if (accountRepository.existsByUsername(adminRequest.getUserId())) {
            throw new AlreadyExistException("이미 존재하는 계정 아이디입니다.");
        }
        return adminRepository.save(Admin.of(adminRequest, accountService));
    }

    public Admin update(Long adminId, AdminRequest adminRequest) {
        Admin admin = adminRepository.findById(adminId).orElseThrow(() -> NotFoundException.of("관리자"));
        if (!admin.getUsername().equals(adminRequest.getUserId()) && accountRepository.existsByUsername(adminRequest.getUserId())) {
            throw new AlreadyExistException("이미 존재하는 계정 아이디입니다.");
        }
        return admin.update(adminRequest);
    }

    public void removeById(Long adminId) {
        Admin admin = adminRepository.findById(adminId).orElseThrow(() -> NotFoundException.of("관리자"));
        adminRepository.delete(admin);
    }
}
