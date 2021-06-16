package com.minsoo.co.tireerpserver.user.entity;

import com.minsoo.co.tireerpserver.user.model.admin.AdminRequest;
import com.minsoo.co.tireerpserver.user.service.AccountService;
import lombok.*;

import javax.persistence.*;

import static lombok.AccessLevel.*;

@Getter
@NoArgsConstructor(access = PROTECTED)
@DiscriminatorValue(value = "ADMIN")
@Entity
@Table(name = "admin")
public class Admin extends Account {

    public Admin(AdminRequest adminRequest, AccountService accountService) {
        super(adminRequest, accountService);
    }

    public static Admin of(AdminRequest adminRequest, AccountService accountService) {
        return new Admin(adminRequest, accountService);
    }
}
