package com.minsoo.co.tireerpserver.service.account;

import com.minsoo.co.tireerpserver.constant.SystemMessage;
import com.minsoo.co.tireerpserver.entity.account.Admin;
import com.minsoo.co.tireerpserver.exception.NotFoundException;
import com.minsoo.co.tireerpserver.repository.account.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    public List<Admin> findAll() {
        return adminRepository.findAll();
    }

    public Admin findById(Long adminId) {
        return adminRepository.findById(adminId).orElseThrow(() -> {
            log.error("Can not find admin by id: {}", adminId);
            return new NotFoundException(SystemMessage.NOT_FOUND + ": [관리자]");
        });
    }

    public Admin create() {

    }
}
