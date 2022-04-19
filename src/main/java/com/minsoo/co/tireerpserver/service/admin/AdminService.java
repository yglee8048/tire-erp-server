package com.minsoo.co.tireerpserver.service.admin;

import com.minsoo.co.tireerpserver.constant.SystemMessage;
import com.minsoo.co.tireerpserver.entity.admin.Admin;
import com.minsoo.co.tireerpserver.exception.BadRequestException;
import com.minsoo.co.tireerpserver.exception.NotFoundException;
import com.minsoo.co.tireerpserver.model.request.admin.AdminCreateRequest;
import com.minsoo.co.tireerpserver.model.request.admin.AdminUpdateRequest;
import com.minsoo.co.tireerpserver.model.response.admin.AdminResponse;
import com.minsoo.co.tireerpserver.repository.account.AccountRepository;
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

    private final AccountRepository accountRepository;
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

    public AdminResponse create(AdminCreateRequest adminCreateRequest) {
        if (accountRepository.existsByUsername(adminCreateRequest.getUserId())) {
            throw new BadRequestException(SystemMessage.USERNAME_DUPLICATE);
        }
        return new AdminResponse(adminRepository.save(Admin.of(adminCreateRequest, passwordEncoder)));
    }

    public AdminResponse update(Long adminId, AdminUpdateRequest adminUpdateRequest) {
        Admin admin = findAdminById(adminId);
        if (!admin.getUsername().equals(adminUpdateRequest.getUserId()) && accountRepository.existsByUsername(adminUpdateRequest.getUserId())) {
            throw new BadRequestException(SystemMessage.USERNAME_DUPLICATE);
        }
        return new AdminResponse(admin.update(adminUpdateRequest, passwordEncoder));
    }

    public void deleteById(Long adminId) {
        Admin admin = findAdminById(adminId);
        adminRepository.delete(admin);
    }

    private Admin findAdminById(Long adminId) {
        return adminRepository.findById(adminId).orElseThrow(() -> new NotFoundException("관리자", adminId));
    }
}
