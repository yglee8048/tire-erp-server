package com.minsoo.co.tireerpserver.user.entity;

import lombok.*;

import javax.persistence.*;

import static lombok.AccessLevel.*;

@Getter
@NoArgsConstructor(access = PROTECTED)
@DiscriminatorValue(value = "ADMIN")
@Entity
@Table(name = "admin")
public class Admin extends Account {
}
