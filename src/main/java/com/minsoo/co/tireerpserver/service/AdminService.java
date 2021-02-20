package com.minsoo.co.tireerpserver.service;

import com.minsoo.co.tireerpserver.api.error.errors.AlreadyExistException;
import com.minsoo.co.tireerpserver.api.error.errors.NotFoundException;
import com.minsoo.co.tireerpserver.model.dto.admin.AdminRequest;
import com.minsoo.co.tireerpserver.model.dto.admin.AdminResponse;
import com.minsoo.co.tireerpserver.model.entity.Admin;
import com.minsoo.co.tireerpserver.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    public List<AdminResponse> findAll() {
        return adminRepository.findAll()
                .stream()
                .map(AdminResponse::of)
                .collect(Collectors.toList());
    }

    public AdminResponse findById(Long id) {
        return AdminResponse.of(adminRepository.findById(id).orElseThrow(NotFoundException::new));
    }

    @Transactional
    public AdminResponse create(AdminRequest createRequest) {
        if (adminRepository.existsByUserId(createRequest.getUserId())) {
            throw new AlreadyExistException("이미 존재하는 아이디입니다.");
        }
        return AdminResponse.of(adminRepository.save(Admin.of(createRequest)));
    }

    @Transactional
    public AdminResponse update(Long id, AdminRequest updateRequest) {
        Admin admin = adminRepository.findById(id).orElseThrow(NotFoundException::new);
        if (!admin.getUserId().equals(updateRequest.getUserId()) && adminRepository.existsByUserId(updateRequest.getUserId())) {
            throw new AlreadyExistException("이미 존재하는 아이디입니다.");
        }
        admin.update(updateRequest);
        return AdminResponse.of(admin);
    }

    @Transactional
    public void removeById(Long id) {
        adminRepository.deleteById(id);
    }
}
