package com.minsoo.co.tireerpserver.model.entity;

import com.minsoo.co.tireerpserver.api.error.errors.ForbiddenException;
import com.minsoo.co.tireerpserver.model.code.AdminRole;
import com.minsoo.co.tireerpserver.model.dto.admin.AdminCreateRequest;
import com.minsoo.co.tireerpserver.model.dto.admin.AdminUpdateRequest;
import com.minsoo.co.tireerpserver.model.entity.embedded.AdminHistory;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "admin", uniqueConstraints = {@UniqueConstraint(name = "user_id_unique", columnNames = {"user_id"})})
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id", length = 20, nullable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "user_pw", nullable = false)
    private String userPw;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 11, nullable = false)
    private AdminRole role;

    @Embedded
    private AdminHistory history;

    private Admin(AdminCreateRequest createRequest, Admin operator) {
        this.userId = createRequest.getUserId();
        this.userPw = createRequest.getUserPw();
        this.name = createRequest.getName();
        this.role = createRequest.getRole();
        this.history = new AdminHistory(operator);
    }

    public static Admin create(AdminCreateRequest createRequest, Admin operator) {
        if (!operator.getRole().equals(AdminRole.SUPER_ADMIN)) {
            throw new ForbiddenException();
        }
        return new Admin(createRequest, operator);
    }

    public void update(AdminUpdateRequest updateRequest, Admin operator) {
        // 비밀번호가 null 인 경우 수정하지 않는다.
        if (updateRequest.getUserPw() != null) {
            this.userPw = updateRequest.getUserPw();
        }
        this.name = updateRequest.getName();
        this.role = updateRequest.getRole();
        this.history = new AdminHistory(operator);
    }
}
