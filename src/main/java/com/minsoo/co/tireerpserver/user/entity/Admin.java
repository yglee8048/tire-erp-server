package com.minsoo.co.tireerpserver.user.entity;

import lombok.*;

import javax.persistence.*;

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

    @Column(name = "description")
    private String description;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;
}
