package com.minsoo.co.tireerpserver.user.api;

import com.minsoo.co.tireerpserver.shared.model.ApiResponse;
import com.minsoo.co.tireerpserver.user.model.admin.AdminRequest;
import com.minsoo.co.tireerpserver.user.model.admin.AdminResponse;
import com.minsoo.co.tireerpserver.user.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1")
@RequiredArgsConstructor
public class AdminApi {

    private final AdminService adminService;

    @GetMapping(value = "/admins")
    @Operation(summary = "관리자 목록 조회")
    public ApiResponse<List<AdminResponse>> findAdmins() {
        return ApiResponse.OK(adminService.findAll()
                .stream()
                .map(AdminResponse::of)
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/admins/{adminId}")
    @Operation(summary = "관리자 상세 조회")
    public ApiResponse<AdminResponse> findAdminById(@PathVariable Long adminId) {
        return ApiResponse.OK(AdminResponse.of(adminService.findById(adminId)));
    }

    @PostMapping(value = "/admins")
    @Operation(summary = "관리자 생성")
    public ResponseEntity<ApiResponse<Void>> createAdmin(@RequestBody @Valid AdminRequest adminRequest) {
        return ApiResponse.CREATED(
                linkTo(methodOn(AdminApi.class).findAdminById(adminService.create(adminRequest).getId())).toUri());
    }

    @PutMapping(value = "/admins/{adminId}")
    @Operation(summary = "관리자 수정")
    public ApiResponse<Void> updateAdmin(@PathVariable Long adminId, @RequestBody @Valid AdminRequest adminRequest) {
        adminService.update(adminId, adminRequest);
        return ApiResponse.NO_CONTENT;
    }

    @DeleteMapping(value = "/admins/{adminId}")
    @Operation(summary = "관리자 삭제")
    public ApiResponse<Void> deleteAdmin(@PathVariable Long adminId) {
        adminService.removeById(adminId);
        return ApiResponse.NO_CONTENT;
    }
}
