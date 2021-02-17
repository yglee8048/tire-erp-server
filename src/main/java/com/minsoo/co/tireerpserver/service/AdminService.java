package com.minsoo.co.tireerpserver.service;

import com.minsoo.co.tireerpserver.api.error.errors.AlreadyExistException;
import com.minsoo.co.tireerpserver.api.error.errors.NotFoundException;
import com.minsoo.co.tireerpserver.model.dto.admin.AdminRequest;
import com.minsoo.co.tireerpserver.model.entity.Admin;
import com.minsoo.co.tireerpserver.repository.AdminRepository;
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

    public List<Admin> findAll() {
        return adminRepository.findAll();
    }

    public Admin findById(Long id) {
        return adminRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public Admin create(AdminRequest createRequest) {
        if (adminRepository.existsByUserId(createRequest.getUserId())) {
            throw new AlreadyExistException("이미 존재하는 아이디입니다.");
        }
        return adminRepository.save(Admin.of(createRequest));
    }

    @Transactional
    public Admin update(Long adminId, AdminRequest updateRequest) {
        Admin admin = this.findById(adminId);
        if (!admin.getUserId().equals(updateRequest.getUserId()) && adminRepository.existsByUserId(updateRequest.getUserId())) {
            throw new AlreadyExistException("이미 존재하는 아이디입니다.");
        }
        admin.update(updateRequest);
        return admin;
    }

    @Transactional
    public void remove(Long id) {
        adminRepository.delete(this.findById(id));
    }
}
