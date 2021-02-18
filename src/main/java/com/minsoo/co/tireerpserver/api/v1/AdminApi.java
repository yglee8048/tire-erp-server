package com.minsoo.co.tireerpserver.api.v1;

import com.minsoo.co.tireerpserver.model.dto.response.ApiResponseDTO;
import com.minsoo.co.tireerpserver.model.dto.admin.AdminRequest;
import com.minsoo.co.tireerpserver.model.dto.admin.AdminResponse;
import com.minsoo.co.tireerpserver.service.AdminService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/admins")
@RequiredArgsConstructor
public class AdminApi {

    private final AdminService adminService;

    @GetMapping
    @ApiOperation(value = "관리자 목록 조회", notes = "관리자의 목록을 조회한다.")
    public ApiResponseDTO<List<AdminResponse>> findAllAdmins() {
        return ApiResponseDTO.createOK(adminService.findAll()
                .stream()
                .map(AdminResponse::of)
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/{adminId}")
    @ApiOperation(value = "관리자 상세 조회", notes = "관리자의 상세 내용을 조회한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "adminId", value = "관리자 ID", example = "201324", required = true)})
    public ApiResponseDTO<AdminResponse> findAdminById(@PathVariable Long adminId) {
        return ApiResponseDTO.createOK(AdminResponse.of(adminService.findById(adminId)));
    }

    @PostMapping
    @ApiOperation(value = "관리자 생성", notes = "관리자를 생성한다.")
    public ApiResponseDTO<AdminResponse> createAdmin(@RequestBody @Valid AdminRequest adminRequest) {
        return ApiResponseDTO.createOK(AdminResponse.of(adminService.create(adminRequest)));
    }

    @PutMapping(value = "/{adminId}")
    @ApiOperation(value = "관리자 수정", notes = "관리자를 수정한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "adminId", value = "관리자 ID", example = "201324", required = true)})
    public ApiResponseDTO<AdminResponse> updateAdmin(@PathVariable Long adminId,
                                                     @RequestBody @Valid AdminRequest updateRequest) {
        return ApiResponseDTO.createOK(AdminResponse.of(adminService.update(adminId, updateRequest)));
    }

    @DeleteMapping(value = "/{adminId}")
    @ApiOperation(value = "관리자 삭제", notes = "관리자를 삭제한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "adminId", value = "관리자 ID", example = "201324", required = true)})
    public ApiResponseDTO<String> deleteAdmin(@PathVariable Long adminId) {
        adminService.remove(adminId);
        return ApiResponseDTO.DEFAULT_OK;
    }
}
