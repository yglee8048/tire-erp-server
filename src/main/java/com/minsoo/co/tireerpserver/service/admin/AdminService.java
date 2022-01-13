package com.minsoo.co.tireerpserver.service.admin;

import com.minsoo.co.tireerpserver.entity.admin.Admin;
import com.minsoo.co.tireerpserver.exception.NotFoundException;
import com.minsoo.co.tireerpserver.model.request.admin.AdminRequest;
import com.minsoo.co.tireerpserver.model.response.admin.AdminResponse;
import com.minsoo.co.tireerpserver.repository.admin.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public List<AdminResponse> findAll() {
        return adminRepository.findAll().stream()
                .map(AdminResponse::new)
                .collect(Collectors.toList());
    }

    public AdminResponse findById(Long adminId) {
        return new AdminResponse(findAdminById(adminId));
    }

    public AdminResponse create(AdminRequest adminRequest) {
        return new AdminResponse(adminRepository.save(Admin.of(adminRequest, passwordEncoder)));
    }

    public AdminResponse update(Long adminId, AdminRequest adminRequest) {
        Admin admin = findAdminById(adminId);
        return new AdminResponse(admin.update(adminRequest, passwordEncoder));
    }

    public void deleteById(Long adminId) {
        Admin admin = findAdminById(adminId);
        adminRepository.delete(admin);
    }

    private Admin findAdminById(Long adminId) {
        return adminRepository.findById(adminId).orElseThrow(() -> new NotFoundException("관리자", adminId));
    }
}
