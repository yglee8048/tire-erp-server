package com.minsoo.co.tireerpserver.api;

import com.minsoo.co.tireerpserver.model.ApiResponse;
import com.minsoo.co.tireerpserver.model.request.admin.AdminCreateRequest;
import com.minsoo.co.tireerpserver.model.request.admin.AdminUpdateRequest;
import com.minsoo.co.tireerpserver.model.response.admin.AdminResponse;
import com.minsoo.co.tireerpserver.service.admin.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AdminApi {

    private final AdminService adminService;

    @GetMapping("/admins")
    public ApiResponse<List<AdminResponse>> findAllAdmins() {
        return ApiResponse.OK(adminService.findAll());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/admins")
    public ApiResponse<AdminResponse> createAdmin(@RequestBody @Valid AdminCreateRequest adminCreateRequest) {
        return ApiResponse.CREATED(adminService.create(adminCreateRequest));
    }

    @GetMapping("/admins/{adminId}")
    public ApiResponse<AdminResponse> findAdminById(@PathVariable Long adminId) {
        return ApiResponse.OK(adminService.findById(adminId));
    }

    @PutMapping("/admins/{adminId}")
    public ApiResponse<AdminResponse> updateAdmin(@PathVariable Long adminId,
                                                  @RequestBody @Valid AdminUpdateRequest adminUpdateRequest) {
        return ApiResponse.OK(adminService.update(adminId, adminUpdateRequest));
    }

    @DeleteMapping("/admins/{adminId}")
    public ApiResponse<Void> deleteAdminById(@PathVariable Long adminId) {
        adminService.deleteById(adminId);
        return ApiResponse.NO_CONTENT();
    }
}
