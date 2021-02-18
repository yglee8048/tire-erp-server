package com.minsoo.co.tireerpserver.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "sale")
public class Sale {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "sale_id", length = 20, nullable = false)
    private Long id;

    @OneToMany(mappedBy = "sale", fetch = LAZY, cascade = ALL, orphanRemoval = true)
    private Set<SaleContent> saleContents = new HashSet<>();
}
