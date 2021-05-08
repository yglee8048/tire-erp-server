package com.minsoo.co.tireerpserver.model.dto.auth;

import com.minsoo.co.tireerpserver.model.entity.Account;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {

    private String name;
    private String email;

    public SessionUser(Account account) {
        this.name = account.getName();
        this.email = account.getEmail();
    }
}
