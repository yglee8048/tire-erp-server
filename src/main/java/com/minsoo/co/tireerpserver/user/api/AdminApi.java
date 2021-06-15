//package com.minsoo.co.tireerpserver.api.v1;
//
//import com.minsoo.co.tireerpserver.global.model.ApiResponse;
//import com.minsoo.co.tireerpserver.model.dto.account.admin.AdminRequest;
//import com.minsoo.co.tireerpserver.model.dto.account.admin.AdminResponse;
//import com.minsoo.co.tireerpserver.account.service.AdminService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.Parameter;
//import io.swagger.v3.oas.annotations.Parameters;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Slf4j
//@RestController
//@RequestMapping(value = "/api/v1/admins")
//@RequiredArgsConstructor
//public class AdminApi {
//
//    private final AdminService adminService;
//
//    @GetMapping
//    @Operation(summary = "관리자 목록 조회", description = "관리자의 목록을 조회한다.")
//    public ApiResponse<List<AdminResponse>> findAllAdmins() {
//        return ApiResponse.OK(adminService.findAll()
//                .stream()
//                .map(AdminResponse::of)
//                .collect(Collectors.toList()));
//    }
//
//    @GetMapping(value = "/{adminId}")
//    @Operation(summary = "관리자 상세 조회", description = "관리자의 상세 내용을 조회한다.")
//    @Parameters({@Parameter(name = "adminId", description = "관리자 ID", example = "201324", required = true)})
//    public ApiResponse<AdminResponse> findAdminById(@PathVariable Long adminId) {
//        return ApiResponse.OK(AdminResponse.of(adminService.findById(adminId)));
//    }
//
//    @PostMapping
//    @Operation(summary = "관리자 생성", description = "관리자를 생성한다.")
//    public ApiResponse<AdminResponse> createAdmin(@RequestBody @Valid AdminRequest adminRequest) {
//        return ApiResponse.OK(AdminResponse.of(adminService.create(adminRequest)));
//    }
//
//    @PutMapping(value = "/{adminId}")
//    @Operation(summary = "관리자 수정", description = "관리자를 수정한다.")
//    @Parameters({@Parameter(name = "adminId", description = "관리자 ID", example = "201324", required = true)})
//    public ApiResponse<Object> updateAdmin(@PathVariable Long adminId,
//                                           @RequestBody @Valid AdminRequest updateRequest) {
//        adminService.update(adminId, updateRequest);
//        return ApiResponse.OK;
//    }
//
//    @DeleteMapping(value = "/{adminId}")
//    @Operation(summary = "관리자 삭제", description = "관리자를 삭제한다.")
//    @Parameters({@Parameter(name = "adminId", description = "관리자 ID", example = "201324", required = true)})
//    public ApiResponse<Object> deleteAdmin(@PathVariable Long adminId) {
//        adminService.removeById(adminId);
//        return ApiResponse.OK;
//    }
//}
