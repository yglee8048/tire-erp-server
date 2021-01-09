package com.minsoo.co.tireerpserver.service;

import com.minsoo.co.tireerpserver.api.error.errors.AlreadyExistException;
import com.minsoo.co.tireerpserver.api.v1.admin.AdminApi;
import com.minsoo.co.tireerpserver.model.code.AdminRole;
import com.minsoo.co.tireerpserver.model.dto.ResponseDTO;
import com.minsoo.co.tireerpserver.model.dto.admin.AdminCreateRequest;
import com.minsoo.co.tireerpserver.model.dto.admin.AdminResponse;
import com.minsoo.co.tireerpserver.model.entity.Admin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
public class AdminApiTest {

    @Autowired
    AdminApi adminApi;

    @Autowired
    AdminService adminService;

    @Test
    public void saveTest() {
        // given
        AdminCreateRequest createRequest = new AdminCreateRequest();
        createRequest.setUserId("test_user");
        createRequest.setUserPw("test_pw");
        createRequest.setName("test_user_name");
        createRequest.setRole(AdminRole.ADMIN);

        // when
        ResponseDTO<AdminResponse> response = adminApi.createAdmin(createRequest);
        Long id = response.getContents().getId();

        // then
        Admin admin = adminService.findById(id);

        assertThat(admin.getUserId()).isEqualTo(createRequest.getUserId());
        assertThat(admin.getUserPw()).isEqualTo(createRequest.getUserPw());
        assertThat(admin.getName()).isEqualTo(createRequest.getName());
        assertThat(admin.getRole()).isEqualTo(createRequest.getRole());
    }

    @Test
    public void userIdDuplicationTest() {
        // given
        AdminCreateRequest createRequest = new AdminCreateRequest();
        createRequest.setUserId("test_user");
        createRequest.setUserPw("test_pw");
        createRequest.setName("test_user_name");
        createRequest.setRole(AdminRole.ADMIN);

        // when
        adminApi.createAdmin(createRequest);

        // then
        assertThatThrownBy(() -> adminApi.createAdmin(createRequest)).isInstanceOf(AlreadyExistException.class);
    }
}
