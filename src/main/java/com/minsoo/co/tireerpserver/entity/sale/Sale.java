package com.minsoo.co.tireerpserver.entity.sale;

import com.minsoo.co.tireerpserver.constant.SaleSource;
import com.minsoo.co.tireerpserver.constant.SaleStatus;
import com.minsoo.co.tireerpserver.entity.BaseTimeEntity;
import com.minsoo.co.tireerpserver.entity.account.ClientCompany;
import com.minsoo.co.tireerpserver.model.request.sale.SaleRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "sale")
public class Sale extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_company_id", referencedColumnName = "client_company_id")
    private ClientCompany clientCompany;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id", referencedColumnName = "delivery_id")
    private Delivery delivery;

    @Enumerated(EnumType.STRING)
    @Column(name = "source")
    private SaleSource source;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SaleStatus status;

    @Column(name = "transaction_date")
    private LocalDate transactionDate;

    @Column(name = "confirmed_date")
    private LocalDate confirmedDate;

    @Column(name = "desired_delivery_date")
    private LocalDate desiredDeliveryDate;

    @OneToMany(mappedBy = "sale", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final Set<SaleContent> saleContents = new HashSet<>();

    @OneToMany(mappedBy = "sale", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final Set<SaleMemo> saleMemos = new HashSet<>();

    private Sale(SaleSource source) {
        this.source = source;
        this.status = SaleStatus.REQUESTED;
    }

    public static Sale of(ClientCompany clientCompany, SaleRequest saleRequest, SaleSource source) {
        Sale sale = new Sale(source);
        return sale.update(clientCompany, saleRequest);
    }

    public Sale update(ClientCompany clientCompany, SaleRequest saleRequest) {
        this.clientCompany = clientCompany;
        this.transactionDate = saleRequest.getTransactionDate();
        this.confirmedDate = saleRequest.getConfirmedDate();
        this.desiredDeliveryDate = saleRequest.getDesiredDeliveryDate();
        return this;
    }

    public Sale confirm() {
        this.status = SaleStatus.CONFIRMED;
        return this;
    }
}
