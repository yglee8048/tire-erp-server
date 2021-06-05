package com.minsoo.co.tireerpserver.services.account.service;

import com.minsoo.co.tireerpserver.api.error.exceptions.AlreadyExistException;
import com.minsoo.co.tireerpserver.api.error.exceptions.NotFoundException;
import com.minsoo.co.tireerpserver.api.v1.model.dto.account.admin.AdminRequest;
import com.minsoo.co.tireerpserver.services.account.entity.Admin;
import com.minsoo.co.tireerpserver.services.account.repository.AdminRepository;
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
    public void update(Long id, AdminRequest updateRequest) {
        Admin admin = adminRepository.findById(id).orElseThrow(NotFoundException::new);
        if (!admin.getUserId().equals(updateRequest.getUserId()) && adminRepository.existsByUserId(updateRequest.getUserId())) {
            throw new AlreadyExistException("이미 존재하는 아이디입니다.");
        }
        admin.update(updateRequest);
    }

    @Transactional
    public void removeById(Long id) {
        adminRepository.deleteById(id);
    }
}
