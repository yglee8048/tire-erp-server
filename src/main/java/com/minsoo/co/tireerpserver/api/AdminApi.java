package com.minsoo.co.tireerpserver.api;

import com.minsoo.co.tireerpserver.model.ApiResponse;
import com.minsoo.co.tireerpserver.model.request.account.AdminRequest;
import com.minsoo.co.tireerpserver.model.response.account.AdminResponse;
import com.minsoo.co.tireerpserver.service.account.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AdminApi {

    private final AdminService adminService;

    @GetMapping("/admins")
    public ApiResponse<List<AdminResponse>> findAllAdmins() {
        return ApiResponse.OK(adminService.findAll()
                .stream()
                .map(AdminResponse::new)
                .collect(Collectors.toList()));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/admins")
    public ApiResponse<AdminResponse> createAdmin(@RequestBody @Valid AdminRequest adminRequest) {
        return ApiResponse.CREATED(new AdminResponse(adminService.create(adminRequest)));
    }

    @GetMapping("/admins/{adminId}")
    public ApiResponse<AdminResponse> findAdminById(@PathVariable Long adminId) {
        return ApiResponse.OK(new AdminResponse(adminService.findById(adminId)));
    }

    @PutMapping("/admins/{adminId}")
    public ApiResponse<AdminResponse> updateAdmin(@PathVariable Long adminId,
                                                  @RequestBody @Valid AdminRequest adminRequest) {
        return ApiResponse.OK(new AdminResponse(adminService.update(adminId, adminRequest)));
    }

    @DeleteMapping("/admins/{adminId}")
    public ApiResponse<Void> deleteAdminById(@PathVariable Long adminId) {
        adminService.deleteById(adminId);
        return ApiResponse.NO_CONTENT();
    }
}
