package com.minsoo.co.tireerpserver.entity.purchase;

import com.minsoo.co.tireerpserver.entity.BaseEntity;
import com.minsoo.co.tireerpserver.entity.management.Vendor;
import com.minsoo.co.tireerpserver.model.request.purchase.PurchaseRequest;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@Table(name = "purchase")
public class Purchase extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", referencedColumnName = "vendor_id")
    private Vendor vendor;

    @Column(name = "transaction_date")
    private LocalDate transactionDate;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "purchase", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<PurchaseContent> purchaseContents = new HashSet<>();

    public static Purchase of(Vendor vendor, PurchaseRequest purchaseRequest) {
        Purchase purchase = new Purchase();
        return purchase.update(vendor, purchaseRequest);
    }

    public Purchase update(Vendor vendor, PurchaseRequest purchaseRequest) {
        this.vendor = vendor;
        this.transactionDate = purchaseRequest.getTransactionDate();
        this.description = purchaseRequest.getDescription();
        return this;
    }
}
