package com.minsoo.co.tireerpserver.purchase.entity;

import com.minsoo.co.tireerpserver.purchase.code.PurchaseStatus;
import com.minsoo.co.tireerpserver.management.entity.Vendor;
import com.minsoo.co.tireerpserver.purchase.model.content.PurchaseContentRequest;
import com.minsoo.co.tireerpserver.tire.entity.TireDot;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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

    @Column(name = "transaction_date", nullable = false)
    private LocalDate transactionDate;

    @OneToMany(mappedBy = "purchase", fetch = LAZY, cascade = ALL, orphanRemoval = true)
    private final Set<PurchaseContent> contents = new HashSet<>();

    //== Business ==//
    public Purchase(Vendor vendor, LocalDate transactionDate) {
        this.vendor = vendor;
        this.status = PurchaseStatus.REQUESTED;
        this.transactionDate = transactionDate;
    }

    public static Purchase of(Vendor vendor, LocalDate transactionDate, Map<TireDot, List<PurchaseContentRequest>> contentMap) {
        Purchase purchase = new Purchase(vendor, transactionDate);

        // contents
        contentMap.forEach((tireDot, contentRequests) -> {
            int sumOfPrice = getSumOfPrice(contentRequests);
            long sumOfQuantity = getSumOfQuantity(contentRequests);
            purchase.getContents().add(PurchaseContent.of(purchase, tireDot, sumOfPrice, sumOfQuantity));
        });

        return purchase;
    }

    public Purchase update(Vendor vendor, LocalDate transactionDate, Map<TireDot, List<PurchaseContentRequest>> contentMap) {
        this.vendor = vendor;
        this.transactionDate = transactionDate;

        // contents
        updateContent(contentMap);

        return this;
    }

    public void updateContent(Map<TireDot, List<PurchaseContentRequest>> contentMap) {
        // DELETE
        List<PurchaseContent> removed = this.getContents()
                .stream()
                .filter(purchaseContent -> !contentMap.containsKey(purchaseContent.getTireDot()))
                .collect(Collectors.toList());
        removed.forEach(PurchaseContent::removeFromPurchase);

        // CREATE & UPDATE
        contentMap.forEach((tireDot, contentRequests) -> {
            int sumOfPrice = getSumOfPrice(contentRequests);
            long sumOfQuantity = getSumOfQuantity(contentRequests);

            this.findContentByTireDot(tireDot)
                    .ifPresentOrElse(
                            // if present, update
                            purchaseContent -> purchaseContent.update(tireDot, sumOfPrice, sumOfQuantity),
                            // if not, create and add
                            () -> this.getContents()
                                    .add(PurchaseContent.of(this, tireDot, sumOfPrice, sumOfQuantity)));
        });
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