package com.minsoo.co.tireerpserver.sale.entity;

import com.minsoo.co.tireerpserver.sale.model.SaleRequest;
import com.minsoo.co.tireerpserver.sale.model.content.SaleContentRequest;
import com.minsoo.co.tireerpserver.sale.code.SaleSource;
import com.minsoo.co.tireerpserver.sale.code.SaleStatus;
import com.minsoo.co.tireerpserver.shared.error.exceptions.AlreadyConfirmedException;
import com.minsoo.co.tireerpserver.tire.entity.TireDot;
import com.minsoo.co.tireerpserver.user.entity.ClientCompany;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static javax.persistence.CascadeType.*;
import static javax.persistence.EnumType.*;
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

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "client_company_id", nullable = false)
    private ClientCompany clientCompany;

    @OneToOne(fetch = LAZY, cascade = ALL, orphanRemoval = true)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @Enumerated(STRING)
    @Column(name = "source", nullable = false)
    private SaleSource source;

    @Enumerated(STRING)
    @Column(name = "status", nullable = false)
    private SaleStatus status;

    @Column(name = "transaction_date", nullable = false)
    private LocalDate transactionDate;

    @Column(name = "confirmed_date")
    private LocalDate confirmedDate;

    @Column(name = "desired_delivery_date")
    private LocalDate desiredDeliveryDate;

    @OneToMany(mappedBy = "sale", fetch = LAZY, cascade = ALL, orphanRemoval = true)
    private final Set<SaleContent> contents = new HashSet<>();

    @OneToMany(mappedBy = "sale", fetch = LAZY, cascade = ALL, orphanRemoval = true)
    private final Set<SaleMemo> memos = new HashSet<>();

    //== Business ==//
    private Sale(ClientCompany clientCompany, SaleRequest saleRequest) {
        this.clientCompany = clientCompany;
        this.source = SaleSource.MANUAL;
        this.status = SaleStatus.REQUESTED;
        this.delivery = null;
        this.transactionDate = saleRequest.getTransactionDate();
        this.confirmedDate = saleRequest.getConfirmedDate();
        this.desiredDeliveryDate = saleRequest.getDesiredDeliveryDate();
    }

    public static Sale of(ClientCompany clientCompany, SaleRequest saleRequest, Map<TireDot, List<SaleContentRequest>> contentMap) {
        Sale sale = new Sale(clientCompany, saleRequest);

        contentMap.forEach((tireDot, contentRequests) -> {
            int sumOfPrice = getSumOfPrice(contentRequests);
            long sumOfQuantity = getSumOfQuantity(contentRequests);
            sale.getContents().add(SaleContent.of(sale, tireDot, sumOfPrice, sumOfQuantity));
        });

        return sale;
    }

    public Sale update(ClientCompany clientCompany, SaleRequest saleRequest, Map<TireDot, List<SaleContentRequest>> contentMap) {
        this.clientCompany = clientCompany;
        this.transactionDate = saleRequest.getTransactionDate();
        this.confirmedDate = saleRequest.getConfirmedDate();
        this.desiredDeliveryDate = saleRequest.getDesiredDeliveryDate();

        // contents
        updateContent(contentMap);

        return this;
    }

    private void updateContent(Map<TireDot, List<SaleContentRequest>> contentMap) {
        // DELETE
        List<SaleContent> removed = this.getContents()
                .stream()
                .filter(saleContent -> !contentMap.containsKey(saleContent.getTireDot()))
                .collect(Collectors.toList());
        removed.forEach(SaleContent::removeFromSale);

        // CREATE & UPDATE
        contentMap.forEach((tireDot, contentRequests) -> {
            int sumOfPrice = getSumOfPrice(contentRequests);
            long sumOfQuantity = getSumOfQuantity(contentRequests);

            this.findContentByTireDot(tireDot)
                    .ifPresentOrElse(
                            // if present, update
                            saleContent -> saleContent.update(tireDot, sumOfPrice, sumOfQuantity),
                            // if not, create and add
                            () -> this.getContents()
                                    .add(SaleContent.of(this, tireDot, sumOfPrice, sumOfQuantity)));
        });
    }

    public Sale confirmStatus() {
        this.status = SaleStatus.CONFIRMED;

        return this;
    }

    public Sale cancel() {
        if (!this.status.equals(SaleStatus.REQUESTED)) {
            throw new AlreadyConfirmedException("이미 확정된 매출입니다.");
        }
        this.status = SaleStatus.CANCELED;
        return this;
    }

    public Optional<SaleContent> findContentByTireDot(TireDot tireDot) {
        return this.contents
                .stream()
                .filter(saleContent -> saleContent.getTireDot().equals(tireDot))
                .findAny();
    }

    private static int getSumOfPrice(List<SaleContentRequest> contentRequests) {
        return contentRequests.stream()
                .map(SaleContentRequest::getPrice)
                .reduce(0, Integer::sum);
    }

    private static long getSumOfQuantity(List<SaleContentRequest> contentRequests) {
        return contentRequests.stream()
                .map(SaleContentRequest::getQuantity)
                .reduce(0L, Long::sum);
    }

    public void addDelivery(Delivery delivery) {
        this.delivery = delivery;
    }
}
