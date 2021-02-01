package com.minsoo.co.tireerpserver.model.entity;

import com.minsoo.co.tireerpserver.model.code.AdminRole;
import com.minsoo.co.tireerpserver.model.dto.admin.AdminCreateRequest;
import com.minsoo.co.tireerpserver.model.dto.admin.AdminUpdateRequest;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.EnumType.*;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "admin", uniqueConstraints = {@UniqueConstraint(name = "admin_user_id_unique", columnNames = {"user_id"})})
public class Admin {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "admin_id", length = 20, nullable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "user_pw", nullable = false)
    private String userPw;

    @Column(name = "name")
    private String name;

    @Enumerated(STRING)
    @Column(name = "role", length = 11, nullable = false)
    private AdminRole role;

    private Admin(AdminCreateRequest createRequest) {
        this.userId = createRequest.getUserId();
        this.userPw = createRequest.getUserPw();
        this.name = createRequest.getName();
        this.role = createRequest.getRole();
    }

    public static Admin of(AdminCreateRequest createRequest) {
        return new Admin(createRequest);
    }

    public void update(AdminUpdateRequest updateRequest) {
        // 비밀번호가 null 인 경우 수정하지 않는다.
        if (updateRequest.getUserPw() != null) {
            this.userPw = updateRequest.getUserPw();
        }
        this.name = updateRequest.getName();
        this.role = updateRequest.getRole();
    }
}
