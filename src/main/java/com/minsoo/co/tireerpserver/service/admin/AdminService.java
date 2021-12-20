package com.minsoo.co.tireerpserver.service.admin;

import com.minsoo.co.tireerpserver.constant.SystemMessage;
import com.minsoo.co.tireerpserver.entity.admin.Admin;
import com.minsoo.co.tireerpserver.exception.NotFoundException;
import com.minsoo.co.tireerpserver.model.request.admin.AdminRequest;
import com.minsoo.co.tireerpserver.repository.admin.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public List<Admin> findAll() {
        return adminRepository.findAll();
    }

    public Admin findById(Long adminId) {
        return adminRepository.findById(adminId).orElseThrow(() -> {
            log.error("Can not find admin by id: {}", adminId);
            return new NotFoundException(SystemMessage.NOT_FOUND + ": [관리자]");
        });
    }

    public Admin create(AdminRequest adminRequest) {
        return adminRepository.save(Admin.of(adminRequest, passwordEncoder));
    }

    public Admin update(Long adminId, AdminRequest adminRequest) {
        Admin admin = findById(adminId);
        return admin.update(adminRequest, passwordEncoder);
    }

    public void deleteById(Long adminId) {
        Admin admin = findById(adminId);
        adminRepository.delete(admin);
    }
}
