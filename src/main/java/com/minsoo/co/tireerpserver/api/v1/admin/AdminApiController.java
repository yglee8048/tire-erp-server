package com.minsoo.co.tireerpserver.api.v1.admin;

import com.minsoo.co.tireerpserver.model.dto.ResponseDTO;
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
@RequestMapping(value = "/admin/api/v1")
@RequiredArgsConstructor
public class AdminApiController {

    private final AdminService adminService;

    @GetMapping(value = "/admins")
    public List<AdminResponse> findAll() {
        return adminService.findAll().stream()
                .map(AdminResponse::new)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/admins/{adminId}")
    public ResponseDTO<AdminResponse> findAdminById(@PathVariable Long adminId) {
        AdminResponse adminResponse = new AdminResponse(adminService.findById(adminId));
        return new ResponseDTO<>(adminResponse);
    }

    @PostMapping(value = "/admins")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO<AdminResponse> createAdmin(@RequestBody @Valid AdminCreateRequest adminCreateRequest) {
        AdminResponse adminResponse = new AdminResponse(adminService.create(adminCreateRequest));
        return new ResponseDTO<>(adminResponse);
    }

    @PutMapping(value = "/admins/{adminId}")
    public ResponseDTO<AdminResponse> updateAdmin(@PathVariable Long adminId, @RequestBody @Valid AdminUpdateRequest adminUpdateRequest) {
        AdminResponse adminResponse = new AdminResponse(adminService.update(adminId, adminUpdateRequest));
        return new ResponseDTO<>(adminResponse);
    }

    @DeleteMapping(value = "/admins/{adminId}")
    public void deleteAdmin(@PathVariable Long adminId) {
        adminService.remove(adminId);
    }
}
