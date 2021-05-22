package com.minsoo.co.tireerpserver.model.entity.entities.purchase;

import com.minsoo.co.tireerpserver.model.code.PurchaseStatus;
import com.minsoo.co.tireerpserver.model.entity.entities.management.Vendor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "purchase")
public class Purchase {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "purchase_id", length = 20, nullable = false)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "vendor_id", referencedColumnName = "vendor_id", nullable = false)
    private Vendor vendor;

    @Enumerated(STRING)
    @Column(name = "status", nullable = false)
    private PurchaseStatus status;

    @Column(name = "purchase_date", nullable = false)
    private LocalDate purchaseDate;

    @OneToMany(mappedBy = "purchase", fetch = LAZY, cascade = ALL, orphanRemoval = true)
    private final Set<PurchaseContent> purchaseContents = new HashSet<>();

    //== Business ==//
    public Purchase(Vendor vendor, LocalDate purchaseDate) {
        this.vendor = vendor;
        this.status = PurchaseStatus.REQUESTED;
        this.purchaseDate = purchaseDate;
    }

    public static Purchase of(Vendor vendor, LocalDate purchaseDate) {
        return new Purchase(vendor, purchaseDate);
    }

    public Purchase update(Vendor vendor, LocalDate purchaseDate) {
        this.vendor = vendor;
        this.purchaseDate = purchaseDate;

        return this;
    }
}
