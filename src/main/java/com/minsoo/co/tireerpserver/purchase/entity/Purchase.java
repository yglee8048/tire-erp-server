package com.minsoo.co.tireerpserver.purchase.entity;

import com.minsoo.co.tireerpserver.management.entity.Vendor;
import com.minsoo.co.tireerpserver.purchase.code.PurchaseStatus;
import com.minsoo.co.tireerpserver.purchase.model.content.PurchaseContentRequest;
import com.minsoo.co.tireerpserver.tire.entity.TireDot;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Getter
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

    @Column(name = "transaction_date", nullable = false)
    private LocalDate transactionDate;

    @OneToMany(mappedBy = "purchase", fetch = LAZY, cascade = ALL, orphanRemoval = true)
    private final Set<PurchaseContent> contents = new HashSet<>();

    //== Business ==//
    protected Purchase() {
        this.status = PurchaseStatus.REQUESTED;
    }

    public static Purchase of(Vendor vendor, LocalDate transactionDate) {
        Purchase purchase = new Purchase();
        purchase.update(vendor, transactionDate);
        return purchase;
    }

    public Purchase update(Vendor vendor, LocalDate transactionDate) {
        this.vendor = vendor;
        this.transactionDate = transactionDate;

        return this;
    }

    public Purchase updateStatus(PurchaseStatus status) {
        this.status = status;

        return this;
    }

    public Optional<PurchaseContent> findContentByTireDot(TireDot tireDot) {
        return this.contents
                .stream()
                .filter(purchaseContent -> purchaseContent.getTireDot().equals(tireDot))
                .findAny();
    }

    private static int getSumOfPrice(List<PurchaseContentRequest> contentRequests) {
        return contentRequests.stream()
                .map(PurchaseContentRequest::getPrice)
                .reduce(0, Integer::sum);
    }

    private static long getSumOfQuantity(List<PurchaseContentRequest> contentRequests) {
        return contentRequests.stream()
                .map(PurchaseContentRequest::getQuantity)
                .reduce(0L, Long::sum);
    }
}
