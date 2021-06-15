package com.minsoo.co.tireerpserver.account.entity;

import com.minsoo.co.tireerpserver.account.code.AdminRole;
import com.minsoo.co.tireerpserver.account.model.admin.AdminRequest;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.EnumType.*;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "admin")
public class Admin {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "admin_id", length = 20, nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Enumerated(STRING)
    @Column(name = "role", length = 11, nullable = false)
    private AdminRole role;

    //== Business ==//
    private Admin(AdminRequest createRequest) {
        this.name = createRequest.getName();
        this.description = createRequest.getDescription();
        this.role = createRequest.getRole();
    }

    public static Admin of(AdminRequest createRequest) {
        return new Admin(createRequest);
    }

    public void update(AdminRequest updateRequest) {
        this.name = updateRequest.getName();
        this.description = updateRequest.getDescription();
        this.role = updateRequest.getRole();
    }
}
