package com.minsoo.co.tireerpserver.entity.sale;

import com.minsoo.co.tireerpserver.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "sale")
public class Sale extends BaseTimeEntity {

}
