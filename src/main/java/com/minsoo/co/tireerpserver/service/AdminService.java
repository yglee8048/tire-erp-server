package com.minsoo.co.tireerpserver.service;

import com.minsoo.co.tireerpserver.api.error.errors.AlreadyExistException;
import com.minsoo.co.tireerpserver.api.error.errors.NotFoundException;
import com.minsoo.co.tireerpserver.model.dto.admin.AdminCreateRequest;
import com.minsoo.co.tireerpserver.model.dto.admin.AdminUpdateRequest;
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
    public Admin create(AdminCreateRequest createRequest, Admin operator) {
        if (adminRepository.existsByUserId(createRequest.getUserId())) {
            throw new AlreadyExistException("이미 존재하는 아이디입니다.");
        }
        return adminRepository.save(Admin.create(createRequest, operator));
    }

    @Transactional
    public Admin update(Long id, AdminUpdateRequest updateRequest, Admin operator) {
        Admin admin = this.findById(id);
        if (!admin.getUserId().equals(updateRequest.getUserId()) && adminRepository.existsByUserId(updateRequest.getUserId())) {
            throw new AlreadyExistException("이미 존재하는 아이디입니다.");
        }
        admin.update(updateRequest, operator);
        return admin;
    }

    @Transactional
    public void remove(Long id) {
        adminRepository.delete(this.findById(id));
    }
}
