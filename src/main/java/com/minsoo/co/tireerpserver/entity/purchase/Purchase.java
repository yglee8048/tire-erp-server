package com.minsoo.co.tireerpserver.entity.purchase;

import com.minsoo.co.tireerpserver.constant.SystemMessage;
import com.minsoo.co.tireerpserver.entity.BaseTimeEntity;
import com.minsoo.co.tireerpserver.entity.management.Vendor;
import com.minsoo.co.tireerpserver.exception.BadRequestException;
import com.minsoo.co.tireerpserver.model.purchase.request.PurchaseRequest;
import lombok.Getter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@Table(name = "purchase")
public class Purchase extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", referencedColumnName = "vendor_id")
    private Vendor vendor;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PurchaseStatus status;

    @Column(name = "transaction_date")
    private LocalDate transactionDate;

    @OneToMany(mappedBy = "purchase", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final Set<PurchaseContent> purchaseContents = new HashSet<>();

    protected Purchase() {
        this.status = PurchaseStatus.REQUESTED;
    }

    public static Purchase of(Vendor vendor, PurchaseRequest purchaseRequest) {
        Purchase purchase = new Purchase();
        return purchase.update(vendor, purchaseRequest);
    }

    public Purchase update(Vendor vendor, PurchaseRequest purchaseRequest) {
        if (this.status.equals(PurchaseStatus.CONFIRMED)) {
            throw new BadRequestException(SystemMessage.ALREADY_CONFIRMED);
        }
        this.vendor = vendor;
        this.transactionDate = purchaseRequest.getTransactionDate();
        return this;
    }

    public Purchase confirm() {
        this.status = PurchaseStatus.CONFIRMED;
        return this;
    }

    public boolean isConfirmed() {
        return this.status.equals(PurchaseStatus.CONFIRMED);
    }
}
