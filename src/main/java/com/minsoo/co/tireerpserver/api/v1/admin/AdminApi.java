package com.minsoo.co.tireerpserver.api.v1.admin;

import com.minsoo.co.tireerpserver.model.dto.response.ApiResponseDTO;
import com.minsoo.co.tireerpserver.model.dto.admin.AdminCreateRequest;
import com.minsoo.co.tireerpserver.model.dto.admin.AdminResponse;
import com.minsoo.co.tireerpserver.model.dto.admin.AdminUpdateRequest;
import com.minsoo.co.tireerpserver.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/admin/api/v1/admins")
@RequiredArgsConstructor
public class AdminApi {

    private final AdminService adminService;

    @GetMapping
    public List<AdminResponse> findAllAdmins() {
        return adminService.findAll()
                .stream()
                .map(AdminResponse::of)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/{adminId}")
    public ApiResponseDTO<AdminResponse> findAdminById(@PathVariable Long adminId) {
        return ApiResponseDTO.createOK(AdminResponse.of(adminService.findById(adminId)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseDTO<AdminResponse> createAdmin(@RequestBody @Valid AdminCreateRequest adminCreateRequest) {
        return ApiResponseDTO.createOK(AdminResponse.of(adminService.create(adminCreateRequest)));
    }

    @PutMapping
    public ApiResponseDTO<AdminResponse> updateAdmin(@RequestBody @Valid AdminUpdateRequest adminUpdateRequest) {
        return ApiResponseDTO.createOK(AdminResponse.of(adminService.update(adminUpdateRequest)));
    }

    @DeleteMapping(value = "/{adminId}")
    public ApiResponseDTO<String> deleteAdmin(@PathVariable Long adminId) {
        adminService.remove(adminId);
        return ApiResponseDTO.DEFAULT_OK;
    }
}
